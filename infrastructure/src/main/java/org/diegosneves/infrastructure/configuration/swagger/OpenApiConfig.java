package org.diegosneves.infrastructure.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Classe de configuração para a documentação da API aberta ({@link OpenAPI}).
 * <p>
 * Esta classe configura as informações detalhadas e as tags que são exibidas na documentação do <b>Swagger/OpenAPI</b>.
 * <p>
 * Isso inclui meta-informações como a versão da API, título, descrição, detalhes de contato e tags usadas para agrupar endpoints relacionados.
 * <p>
 *
 * @author diegosneves
 * @since 1.0.0
 */
@Configuration
@Profile("development")
public class OpenApiConfig {

    /**
     * Retorna uma instância personalizada do {@link OpenAPI}.
     * <p>
     * Este método configura informações detalhadas e tags que serão exibidas na documentação Swagger/OpenAPI.
     *
     * @return a instância personalizada do {@link OpenAPI}
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getInfo());
    }


    /**
     * Busca informações sobre a API.
     *
     * @return Uma instância da classe {@link Info} contendo versão, título, descrição e detalhes de contato
     */
    private Info getInfo() {
        return new Info()
                .version("v1.0.0")
                .title("ExactPro CMMS Backend API")
                .description("CMMS API")
                .contact(new Contact().email("neves.diegoalex@outlook.com").url("https://github.com/diegosneves/exactpro-cmms-backend").name("Diego Neves"));
    }

}
