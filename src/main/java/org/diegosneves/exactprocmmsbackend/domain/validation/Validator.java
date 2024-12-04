package org.diegosneves.exactprocmmsbackend.domain.validation;

import org.diegosneves.exactprocmmsbackend.domain.report.PressureVesselReportValidator;

/**
 * Classe abstrata para validação de entidades ou objetos.
 *
 * <p>A classe {@code Validator} define a estrutura básica para um validador,
 * que deve ser estendido por classes concretas que implementam a validação
 * específica de diversos tipos de entidades ou objetos.</p>
 *
 * <p>Essa classe utiliza um {@link ValidationHandler} para reportar erros
 * encontrados durante o processo de validação. O método {@link #validate()}
 * é abstrato e deve ser implementado nas subclasses para realizar a validação
 * específica do objeto em questão.</p>
 *
 * <h3>Exemplo de Uso:</h3>
 * <pre>{@code
 * // Suponha que ClientValidator é uma classe concreta que estende Validator
 * ClientValidator clientValidator = new ClientValidator(client, new ThrowsValidationHandler());
 * clientValidator.validate();
 * }</pre>
 *
 * <h3>Configurações:</h3>
 * <p>A classe {@code Validator} necessita de um {@link ValidationHandler} passado
 * durante sua construção para lidar com os erros de validação.</p>
 *
 * <h3>Erros:</h3>
 * <p>Os erros de validação encontrados devem ser reportados através do {@link ValidationHandler},
 * que é responsável por encapsular a lógica de tratamento desses erros.</p>
 *
 * <h3>Responsabilidades:</h3>
 * <ul>
 *   <li>Definir a interface básica para a validação através do método {@code validate()}.</li>
 *   <li>Providenciar o {@link ValidationHandler} para as subclasses via o método {@link #getValidationHandler()}.</li>
 * </ul>
 *
 * <h3>Estendendo a Classe:</h3>
 * <p>Para implementar um específico validador, uma classe deve estender {@code Validator} e
 * implementar o método {@code validate()}. Dentro desse método, utilize o {@link ValidationHandler}
 * para reportar quaisquer inconsistências detectadas.</p>
 *
 * @author diegoneves
 * @see ValidationHandler
 * @since 1.0.0
 */
public abstract class Validator {

    private final ValidationHandler validationHandler;

    /**
     * Construtor protegido para a classe {@code Validator}.
     *
     * <p>Inicializa uma nova instância de {@code Validator} utilizando o {@link ValidationHandler} fornecido. Este
     * construtor é chamado pelas subclasses para inicializar o manipulador de validação que será utilizado
     * para registrar e relatar erros de validação encontrados durante o processo de validação.</p>
     *
     * <h3>Parâmetros:</h3>
     * <ul>
     *   <li>{@code validationHandler} - o manipulador de validação responsável por gerenciar e reportar
     *   os erros de validação. Não deve ser {@code null}.</li>
     * </ul>
     *
     * <h3>Exemplo de Subclasses:</h3>
     * <pre>{@code
     * // Suponha que ClientValidator é uma classe concreta que estende Validator
     * public class ClientValidator extends Validator {
     *     public ClientValidator(Client client, ValidationHandler validationHandler) {
     *         super(validationHandler);
     *         this.client = client;
     *     }
     *
     *     @Override
     *     public void validate() {
     *         // Implementação da validação do cliente
     *     }
     * }
     * }</pre>
     *
     * @param validationHandler o manipulador de validação que irá coletar e gerenciar os erros de validação
     *                          durante o processo. Não deve ser {@code null}.
     */
    protected Validator(ValidationHandler validationHandler) {
        this.validationHandler = validationHandler;
    }

    /**
     * Valida o estado atual da entidade ou objeto.
     *
     * <p>Este método é abstrato e deve ser implementado pelas subclasses para
     * realizar a validação específica do objeto em questão. A implementação deste
     * método deve utilizar o {@link ValidationHandler} para reportar quaisquer
     * erros de validação encontrados.</p>
     *
     * <p><strong>Exemplo de uso:</strong></p>
     * <pre> {@code
     *     // Suponha que ClientValidator é uma classe concreta que estende Validator
     *     ClientValidator clientValidator = new ClientValidator(client, new ThrowsValidationHandler());
     *     clientValidator.validate();
     * }
     * </pre>
     *
     * <p><strong>Erros de Validação:</strong></p>
     * <ul>
     *   <li>Se os dados do cliente são nulos ou vazio (ex: CNPJ, nome da empresa, ramo de atividade, setor).</li>
     *   <li>Se as restrições específicas de negócio não são atendidas (ex: formato do CNPJ válido).</li>
     * </ul>
     *
     * <h3>Parâmetros:</h3>
     * Este método abstrato não possui parâmetros.
     *
     * <h3>Retorno:</h3>
     * Este método não possui valor de retorno. Todas as mensagens de erro devem ser
     * reportadas através do {@link ValidationHandler} associado.
     *
     * <h3>Exceções:</h3>
     * A implementação pode lançar exceções específicas de validação ou de domínio
     * através do {@link ValidationHandler}, mas não diretamente neste método abstrato.
     *
     * @see ValidationHandler
     */
    public abstract void validate();

    /**
     * Retorna o manipulador de validação associado a esta instância do {@code Validator}.
     *
     * <p>Este método permite acesso ao {@link ValidationHandler} que é responsável por
     * gerenciar e reportar os erros de validação encontrados durante o processo de validação.
     * O {@code ValidationHandler} agregado é crucial para o funcionamento correto das validações,
     * pois encapsula a lógica de relatório de erros.</p>
     *
     * <h3>Retorno:</h3>
     * <ul>
     *   <li>Um {@code ValidationHandler} que é utilizado para registrar e manipular os erros
     *   de validação.</li>
     * </ul>
     *
     * <h3>Exemplo de uso:</h3>
     * <pre>{@code
     *     // Suponha que PressureVesselReportValidator é uma classe concreta que estende Validator
     *     PressureVesselReportValidator validator = new PressureVesselReportValidator(report, new ThrowsValidationHandler());
     *     ValidationHandler handler = validator.getValidationHandler();
     *     if (handler.hasError()) {
     *         // processa os erros de validação
     *     }
     * }</pre>
     *
     * <h3>Subclasses Conhecidas:</h3>
     * <ul>
     *   <li>{@link PressureVesselReportValidator}</li>
     * </ul>
     *
     * @return {@link ValidationHandler} associado a esta instância do {@code Validator}.
     */
    protected ValidationHandler getValidationHandler() {
        return this.validationHandler;
    }

}
