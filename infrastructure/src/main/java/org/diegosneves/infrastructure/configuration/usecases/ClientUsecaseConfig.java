package org.diegosneves.infrastructure.configuration.usecases;

import org.diegosneves.application.client.create.CreateClientUseCase;
import org.diegosneves.application.client.create.DefaultCreateClientUseCase;
import org.diegosneves.application.client.delete.DefaultDeleteClientUseCase;
import org.diegosneves.application.client.delete.DeleteClientUseCase;
import org.diegosneves.application.client.retrieve.get.DefaultGetClientByIdUseCase;
import org.diegosneves.application.client.retrieve.get.GetClientByIdUseCase;
import org.diegosneves.application.client.retrieve.list.DefaultListClientsUseCase;
import org.diegosneves.application.client.retrieve.list.ListClientsUseCase;
import org.diegosneves.application.client.update.DefaultUpdateClientUseCase;
import org.diegosneves.application.client.update.UpdateClientUseCase;
import org.diegosneves.domain.client.ClientGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientUsecaseConfig {

    private final ClientGateway gateway;

    public ClientUsecaseConfig(@Autowired final ClientGateway gateway) {
        this.gateway = gateway;
    }

    @Bean
    public CreateClientUseCase createClientUseCase() {
        return new DefaultCreateClientUseCase(this.gateway);
    }

    @Bean
    public DeleteClientUseCase deleteClientUseCase() {
        return new DefaultDeleteClientUseCase(this.gateway);
    }

    @Bean
    public GetClientByIdUseCase getClientByIdUseCase() {
        return new DefaultGetClientByIdUseCase(this.gateway);
    }

    @Bean
    public ListClientsUseCase listClientsUseCase() {
        return new DefaultListClientsUseCase(this.gateway);
    }

    @Bean
    public UpdateClientUseCase updateClientUseCase() {
        return new DefaultUpdateClientUseCase(this.gateway);
    }

}
