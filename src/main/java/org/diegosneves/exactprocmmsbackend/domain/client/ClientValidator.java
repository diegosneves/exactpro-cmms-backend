package org.diegosneves.exactprocmmsbackend.domain.client;

import org.diegosneves.exactprocmmsbackend.domain.utils.FiscalValidatorUtil;
import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;
import org.diegosneves.exactprocmmsbackend.domain.validation.Validator;

/**
 * A classe {@link ClientValidator} é responsável por validar os dados de um cliente
 * específico em relação aos requisitos de {@code CNPJ}, ramo de atividade, nome da
 * empresa e setor da empresa. Esta classe herda de {@link Validator} e utiliza
 * um {@link ValidationHandler} para coletar e manipular os erros de validação
 * encontrados.
 *
 * <p>Os principais métodos desta classe incluem:</p>
 * <ul>
 *   <li>{@link #validate()} - Método principal de validação que executa todas
 *   as validações específicas do cliente.</li>
 *   <li>{@link #checkCnpjConstraints()} - Verifica a validade do CNPJ do cliente.</li>
 *   <li>{@link #checkCompanyBranchConstraints()} - Verifica se o ramo de
 *   atividade da empresa está preenchido corretamente.</li>
 *   <li>{@link #checkCompanyNameConstraints()} - Verifica se o nome da empresa
 *   está preenchido corretamente.</li>
 *   <li>{@link #checkCompanySectorConstraints()} - Verifica se o setor da
 *   empresa está preenchido corretamente.</li>
 * </ul>
 *
 * <p>Exemplo de uso:</p>
 * <pre> {@code
 *     Client client = new Client("01234567890123", "Tech Solutions", "Tecnologia", "Desenvolvimento");
 *     client.validate(new ThrowsValidationHandler())
 *  }
 * </pre>
 *
 * @author diegoneves
 * @see org.diegosneves.exactprocmmsbackend.domain.validation.handler.ThrowsValidationHandler
 * @since 1.0.0
 */
public class ClientValidator extends Validator {

    private final Client client;

    /**
     * Construtor para a classe {@code ClientValidator}.
     *
     * <p>Inicializa uma nova instância de {@code ClientValidator} utilizando o cliente fornecido e o manipulador
     * de validação. Este construtor estende a classe {@link Validator} e, como tal, invoca o construtor
     * da classe base com o {@code ValidationHandler} fornecido.</p>
     *
     * <p>Este construtor é projetado para preparar a instância de {@code ClientValidator} para
     * executar diversas verificações de validação relacionadas ao cliente, acessando atributos do cliente
     * e reportando erros conforme necessário através do {@code ValidationHandler} associado.</p>
     *
     * @param client            o cliente a ser validado. Este parâmetro não pode ser {@code null}, pois
     *                          todas as validações serão realizadas com base nos atributos deste cliente.
     * @param validationHandler o manipulador de validação que irá coletar e gerenciar os erros
     *                          de validação durante o processo. Não deve ser {@code null}.
     */
    public ClientValidator(Client client, final ValidationHandler validationHandler) {
        super(validationHandler);
        this.client = client;
    }

    @Override
    public void validate() {
        this.checkCnpjConstraints();
        this.checkCompanyBranchConstraints();
        this.checkCompanyNameConstraints();
        this.checkCompanySectorConstraints();
    }

    /**
     * Verifica as restrições do CNPJ do cliente.
     *
     * <p>Este método utiliza a classe {@link FiscalValidatorUtil} para validar o CNPJ
     * do cliente. Se o CNPJ for inválido, um erro de validação será registrado no
     * {@link ValidationHandler} associado a esta instância de {@link ClientValidator}.
     *
     * <p>Não há parâmetros de entrada para este método, pois ele opera diretamente sobre
     * o atributo {@code client} da classe.</p>
     *
     * <p>Este método é projetado para ser chamado como parte do processo abrangente de
     * validação do cliente, geralmente dentro do método {@link #validate()}.</p>
     *
     * @see #validate()
     * @see FiscalValidatorUtil#isCnpjValid(String)
     */
    private void checkCnpjConstraints() {
        try {
            FiscalValidatorUtil.isCnpjValid(this.client.getCnpj());
        } catch (Exception e) {
            this.getValidationHandler().append(new ErrorData(e.getMessage()));
        }
    }

    /**
     * Verifica as restrições do setor da empresa do cliente.
     *
     * <p>Este método valida o setor da empresa associado ao cliente. Se o setor
     * estiver nulo, vazio ou contiver apenas espaços em branco, um erro de
     * validação será adicionado ao {@link ValidationHandler} associado.</p>
     *
     * <h3>Erros de Validação:</h3>
     * <ul>
     *   <li>Se o setor da empresa é nulo.</li>
     *   <li>Se o setor da empresa é uma string vazia.</li>
     *   <li>Se o setor da empresa contém apenas espaços em branco.</li>
     * </ul>
     *
     * <h3>Exceções:</h3>
     * Este método não lança exceções diretamente, mas pode adicionar erros de
     * validação ao {@link ValidationHandler}, o que pode resultar em exceções
     * sendo lançadas posteriormente no fluxo de validação.
     *
     * <p>Este método é projetado para ser chamado como parte do processo
     * abrangente de validação do cliente, geralmente dentro do método
     * {@link #validate()}.</p>
     *
     * @see #validate()
     * @see Client#getCompanySector()
     * @see ValidationHandler#append(ErrorData)
     */
    private void checkCompanySectorConstraints() {
        final var companySector = this.client.getCompanySector();
        if (companySector == null || companySector.isEmpty() || companySector.isBlank()) {
            this.getValidationHandler().append(new ErrorData("Company sector is required"));
        }
    }

    /**
     * Verifica as restrições do nome da empresa do cliente.
     *
     * <p>Este método valida o nome da empresa associado ao cliente. Se o nome
     * da empresa estiver nulo, vazio ou contiver apenas espaços em branco,
     * um erro de validação será adicionado ao {@link ValidationHandler}
     * associado.</p>
     *
     * <h3>Erros de Validação:</h3>
     * <ul>
     *   <li>Se o nome da empresa é nulo.</li>
     *   <li>Se o nome da empresa é uma string vazia.</li>
     *   <li>Se o nome da empresa contém apenas espaços em branco.</li>
     * </ul>
     *
     * <h3>Exceções:</h3>
     * Este método não lança exceções diretamente, mas pode adicionar erros de
     * validação ao {@link ValidationHandler}, o que pode resultar em exceções
     * sendo lançadas posteriormente no fluxo de validação.
     *
     * <p>Este método é projetado para ser chamado como parte do processo
     * abrangente de validação do cliente, geralmente dentro do método
     * {@link #validate()}.</p>
     *
     * @see #validate()
     * @see Client#getCompanyName()
     * @see ValidationHandler#append(ErrorData)
     */
    private void checkCompanyNameConstraints() {
        final String companyName = this.client.getCompanyName();
        if (companyName == null || companyName.isEmpty() || companyName.isBlank()) {
            this.getValidationHandler().append(new ErrorData("Company name is required"));
        }
    }

    /**
     * Verifica as restrições da filial da empresa do cliente.
     *
     * <p>Este método valida a filial da empresa associada ao cliente. Se a filial estiver nula, vazia
     * ou contiver apenas espaços em branco, um erro de validação será adicionado ao
     * {@link ValidationHandler} associado.</p>
     *
     * <h3>Erros de Validação:</h3>
     * <ul>
     *   <li>Se a filial da empresa é nula.</li>
     *   <li>Se a filial da empresa é uma string vazia.</li>
     *   <li>Se a filial da empresa contém apenas espaços em branco.</li>
     * </ul>
     *
     * <h3>Exceções:</h3>
     * Este método não lança exceções diretamente, mas pode adicionar erros de validação ao
     * {@link ValidationHandler}, o que pode resultar em exceções sendo lançadas posteriormente
     * no fluxo de validação.
     *
     * <p>Este método é projetado para ser chamado como parte do processo abrangente de validação do
     * cliente, geralmente dentro do método {@link #validate()}.</p>
     *
     * @see Client#getCompanyBranch()
     * @see #validate()
     * @see ValidationHandler#append(ErrorData)
     */
    private void checkCompanyBranchConstraints() {
        final var companyBranch = this.client.getCompanyBranch();
        if (companyBranch == null || companyBranch.isEmpty() || companyBranch.isBlank()) {
            this.getValidationHandler().append(new ErrorData("Company branch is required"));
        }
    }
}
