package org.diegosneves.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.API;
import org.diegosneves.application.client.create.CreateClientOutput;
import org.diegosneves.application.client.create.CreateClientUseCase;
import org.diegosneves.application.client.retrieve.get.ClientOutput;
import org.diegosneves.application.client.retrieve.get.GetClientByIdUseCase;
import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.domain.exceptions.DomainException;
import org.diegosneves.domain.exceptions.NotFoundException;
import org.diegosneves.domain.validation.ErrorData;
import org.diegosneves.domain.validation.handler.Notification;
import org.diegosneves.infrastructure.ControllerTest;
import org.diegosneves.infrastructure.address.model.CreateAddressApiInput;
import org.diegosneves.infrastructure.client.model.CreateClientApiInput;
import org.diegosneves.infrastructure.contact.model.CreateContactApiInput;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ControllerTest(controllers = ClientAPI.class)
class ClientAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateClientUseCase createClientUseCase;

    @MockBean
    private GetClientByIdUseCase getClientByIdUseCase;

    @Test
    void shouldCreateClientGivenValidInput() throws Exception {
        final var expectedCnpj = "34494244000190";
        final var expectedAddress = buildAddressApiInputForTests();
        final var expectedContact = buildContactApiInputForTests();
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";

        final var anInput = new CreateClientApiInput(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        when(this.createClientUseCase.execute(any())).thenReturn(API.Right(new CreateClientOutput(ClientID.from("123"))));
        final var request = post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(anInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/clients/123"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientID.value").value("123"));

        verify(this.createClientUseCase, times(1)).execute(argThat(input -> Objects.equals(expectedCnpj, input.cnpj())));
    }

    private static CreateContactApiInput buildContactApiInputForTests() {
        return new CreateContactApiInput("email@email.com", "12334567896");
    }

    private static CreateAddressApiInput buildAddressApiInputForTests() {
        return new CreateAddressApiInput("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
    }

    @Test
    void givenAnInvalidCnpjWhenCallsTheCreatedClientUseCaseMethodThenShouldReturnAnErrorMessage() throws Exception {
        final var expectedCnpj = "34494244000190";
        final var expectedAddress = buildAddressApiInputForTests();
        final var expectedContact = buildContactApiInputForTests();
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";
        final var expectedErrorMessage = String.format("O CNPJ %s já foi cadastrado!", expectedCnpj);


        final var anInput = new CreateClientApiInput(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        when(this.createClientUseCase.execute(any())).thenReturn(API.Left(Notification.create(new ErrorData(expectedErrorMessage))));
        final var request = post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(anInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value(expectedErrorMessage));

        verify(this.createClientUseCase, times(1)).execute(argThat(input -> Objects.equals(expectedCnpj, input.cnpj())));
    }

    @Test
    void givenAnInvalidCnpjWhenCallsTheCreatedClientUseCaseMethodThenShouldReturnADomainException() throws Exception {
        final var expectedCnpj = "34494244000190";
        final var expectedAddress = buildAddressApiInputForTests();
        final var expectedContact = buildContactApiInputForTests();
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";
        final var expectedErrorMessage = String.format("O CNPJ %s já foi cadastrado!", expectedCnpj);


        final var anInput = new CreateClientApiInput(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        when(this.createClientUseCase.execute(any())).thenThrow(DomainException.with(new ErrorData(expectedErrorMessage)));
        final var request = post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(anInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value(expectedErrorMessage));

        verify(this.createClientUseCase, times(1)).execute(argThat(input -> Objects.equals(expectedCnpj, input.cnpj())));
    }

    @Test
    void shouldBeRetrievedFromGatewayAValidClientWhenReceiveAValidClientId() throws Exception {
        final var expectedCnpj = "34494244000190";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";

        final var client = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var expectedId = client.getId().getValue();

        when(this.getClientByIdUseCase.execute(any())).thenReturn(ClientOutput.from(client));
        final var request = get("/clients/{id}", expectedId);

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.print());

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedId));


    }

    @Test
    void shouldNotBeRetrievedFromGatewayAValidClientWhenReceiveAnInvalidClientId() throws Exception {
        final var expectedId = ClientID.from("123456789");
        final var expectedErrorMessage = NotFoundException.ENTITY_NOT_FOUND_MESSAGE.formatted(Client.class.getSimpleName(), expectedId.getValue());

        when(this.getClientByIdUseCase.execute(any())).thenThrow(NotFoundException.with(Client.class, expectedId));
        final var request = get("/clients/{id}", expectedId);

        final var response = this.mvc.perform(request).andDo(MockMvcResultHandlers.print());

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedErrorMessage));
    }

}
