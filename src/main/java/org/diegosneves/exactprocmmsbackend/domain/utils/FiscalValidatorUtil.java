package org.diegosneves.exactprocmmsbackend.domain.utils;

import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.ThrowsValidationHandler;

/**
 * Utilitário para validação de CNPJ (Cadastro Nacional da Pessoa Jurídica) Brasileiro.
 * <p>
 * Esta classe fornece métodos para validar vários aspectos de um número de CNPJ,
 * incluindo comprimento, dígitos verificadores e se todos os dígitos são iguais.
 * <p>
 * <p>Regras de validação:</p>
 *   <ul>
 *      <li>O CNPJ deve ter 14 dígitos.</li>
 *      <li>O CNPJ não deve ser nulo ou vazio.</li>
 *      <li>Os dígitos verificadores (12º e 13º dígitos) devem ser válidos.</li>
 *      <li>O CNPJ não deve conter todos os dígitos iguais.</li>
 *   </ul>
 *
 *   <p>Métodos públicos:</p>
 * <ul>
 *     <li>{@code validateCnpj(String cnpj)}: Valida e limpa o CNPJ fornecido, verificando todos os critérios acima.</li>
 * </ul>
 *
 *   <p>Constantes utilizadas:</p>
 *   <ul>
 *     <li>{@code NUMBERS_ONLY_REGEX}: Expressão regular para remover todos os caracteres que não são números.</li>
 *     <li>{@code EMPTY_STRING}: String vazia utilizada nos métodos de limpeza do CNPJ.</li>
 *     <li>{@code REPEATED_DIGIT_PATTERN}: Padrão para detectar se todos os dígitos do CNPJ são iguais.</li>
 *     <li>{@code CNPJ_LENGTH_LIMIT}: Limite de comprimento válido para um CNPJ (14 dígitos).</li>
 *    <li>Mensagens de erro para várias validações (CNPJ nulo/vazio, comprimento inválido, dígitos verificadores inválidos, dígitos todos iguais).</li>
 *   </ul>
 * Exemplo de uso:
 * <pre>
 * {@code
 * String cnpjValido = FiscalValidatorUtil.validateCnpj("12.345.678/9012-34");
 * }
 * </pre>
 *
 * @author diegoneves
 * @since 1.0.0
 */
public class FiscalValidatorUtil {

    private static final String NUMBERS_ONLY_REGEX = "[^0-9]";
    private static final String EMPTY_STRING = "";
    private static final String REPEATED_DIGIT_PATTERN = "(\\d)\\1*";
    private static final int CNPJ_LENGTH_LIMIT = 14;
    private static final String CNPJ_NULL_OR_EMPTY_ERROR = "O CNPJ não deve ser nulo ou vazio";
    private static final String INVALID_CNPJ_LENGTH = "CNPJ deve ter 14 dígitos";
    private static final String INVALID_FIRST_DIGIT = "O primeiro dígito verificador do CNPJ não é válido.";
    private static final String INVALID_SECOND_DIGIT = "O segundo dígito verificador do CNPJ não é válido.";
    private static final String CNPJ_ALL_DIGITS_SAME_ERROR = "CNPJ não deve conter todos os dígitos iguais";

    private static ValidationHandler validationHandler = new ThrowsValidationHandler();

    private FiscalValidatorUtil() {
    }

    /**
     * Valida um número de CNPJ (Cadastro Nacional da Pessoa Jurídica).
     *
     * <p>Este método realiza a validação completa de um CNPJ, executando as seguintes etapas:</p>
     * <ol>
     *   <li>Limpeza e validação inicial com {@link #validateAndCleanCnpj(String)}</li>
     *   <li>Validação do comprimento com {@link #validateCnpjLength(String)}</li>
     *   <li>Validação do primeiro dígito verificador com {@link #validateFirstCheckDigit(String)}</li>
     *   <li>Validação do segundo dígito verificador com {@link #validateSecondCheckDigit(String)}</li>
     *   <li>Verificação se todos os dígitos são iguais com {@link #validateCnpjAllDigitsSame(String)}</li>
     * </ol>
     *
     * @param cnpj O número do CNPJ como uma string que pode conter formatação ou separadores.
     * @return O CNPJ limpo e validado, contendo apenas dígitos.
     * @throws IllegalArgumentException se o CNPJ for inválido em qualquer das etapas de validação.
     */
    public static String validateCnpj(String cnpj) {
        String cleanCnpj = validateAndCleanCnpj(cnpj);
        validateCnpjLength(cleanCnpj);
        validateFirstCheckDigit(cleanCnpj);
        validateSecondCheckDigit(cleanCnpj);
        validateCnpjAllDigitsSame(cleanCnpj);
        return cleanCnpj;
    }

    public static void isCnpjValid(String cnpj) {
        validateCnpj(cnpj);
    }

    /**
     * Valida o primeiro dígito verificador de um dado CNPJ (Cadastro Nacional da Pessoa Jurídica).
     * Um CNPJ é composto de 14 dígitos, onde o 13º é o primeiro dígito verificador.
     * <p>
     * Este método calcula o primeiro dígito verificador e o compara com o 13º dígito na entrada.
     * Se o dígito verificador calculado não corresponder ao dígito fornecido, um erro é anexado ao
     * {@link ValidationHandler validation handler}.
     *
     * @param cnpj O número do CNPJ como uma string sem qualquer formatação ou separadores.
     */
    private static void validateFirstCheckDigit(String cnpj) {
        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int resto = soma % 11;
        int digito = (resto < 2) ? 0 : 11 - resto;
        if (digito != Character.getNumericValue(cnpj.charAt(12))) {
            validate(INVALID_FIRST_DIGIT);
        }
    }

    /**
     * Valida o segundo dígito verificador de um dado CNPJ (Cadastro Nacional da Pessoa Jurídica).
     * Um CNPJ é composto de 14 dígitos, onde o 14º é o segundo dígito verificador.
     * <p>
     * Este método calcula o segundo dígito verificador e o compara com o 14º dígito na entrada.
     * Se o dígito verificador calculado não corresponder ao dígito fornecido, um erro é anexado ao
     * {@link ValidationHandler validation handler}.
     *
     * @param cnpj O número do CNPJ como uma string sem qualquer formatação ou separadores.
     */
    private static void validateSecondCheckDigit(String cnpj) {
        int soma = 0;
        int peso = 2;
        for (int i = 12; i >= 0; i--) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int resto = soma % 11;
        int digito = (resto < 2) ? 0 : 11 - resto;
        if (digito != Character.getNumericValue(cnpj.charAt(13))) {
            validate(INVALID_SECOND_DIGIT);
        }
    }

    /**
     * Valida o comprimento do CNPJ (Cadastro Nacional da Pessoa Jurídica).
     * <p>
     * Este método verifica se a string de CNPJ fornecida tem o comprimento
     * exato especificado em {@code CNPJ_LENGTH_LIMIT}. Se o comprimento não for
     * igual ao limite definido, um erro é anexado ao {@link ValidationHandler}.
     * </p>
     *
     * @param cnpj O número do CNPJ como uma string sem qualquer formatação ou separadores.
     */
    private static void validateCnpjLength(String cnpj) {
        if (cnpj.length() != CNPJ_LENGTH_LIMIT) {
            validate(INVALID_CNPJ_LENGTH);
        }
    }

    /**
     * Valida e limpa um número de CNPJ (Cadastro Nacional da Pessoa Jurídica).
     *
     * <p>Este método realiza as seguintes ações:</p>
     * <ul>
     *   <li>Verifica se o CNPJ fornecido é nulo, vazio ou contém apenas espaços em branco.</li>
     *   <li>Remove qualquer caractere não numérico do CNPJ.</li>
     *   <li>Remove espaços em branco no início e no fim da string.</li>
     * </ul>
     *
     * <p>Se o CNPJ fornecido for nulo, vazio ou contiver apenas espaços em branco, a função
     * {@link #validate(String)} é chamada com a mensagem de erro correspondente.</p>
     *
     * @param cnpj O número do CNPJ como uma string que pode conter formatação ou separadores.
     * @return O CNPJ limpo, contendo apenas dígitos.
     */
    private static String validateAndCleanCnpj(String cnpj) {
        if (cnpj == null || cnpj.isEmpty() || cnpj.isBlank()) {
            validate(CNPJ_NULL_OR_EMPTY_ERROR);
        }
        assert cnpj != null;
        return cnpj.replaceAll(NUMBERS_ONLY_REGEX, EMPTY_STRING).trim();
    }

    /**
     * Valida se todos os dígitos de um dado CNPJ (Cadastro Nacional da Pessoa Jurídica) são iguais.
     * <p>
     * Este método utiliza uma expressão regular definida em {@code REPEATED_DIGIT_PATTERN} para verificar
     * se todos os dígitos do CNPJ fornecido são os mesmos. Caso sejam, um erro é anexado ao {@link ValidationHandler}
     * utilizando a mensagem de erro definida em {@code CNPJ_ALL_DIGITS_SAME_ERROR}.
     * </p>
     *
     * @param cnpj O número do CNPJ como uma string sem qualquer formatação ou separadores.
     * @see FiscalValidatorUtil#validate(String) Método utilizado para registrar a mensagem de erro.
     */
    private static void validateCnpjAllDigitsSame(String cnpj) {
        if (cnpj.matches(REPEATED_DIGIT_PATTERN)) {
            validate(CNPJ_ALL_DIGITS_SAME_ERROR);
        }
    }

    /**
     * Adiciona uma mensagem de erro ao {@link ValidationHandler}.
     *
     * <p>Este método cria uma nova instância de {@link ErrorData} com a mensagem de erro fornecida
     * e a adiciona ao {@code validationHandler}.</p>
     *
     * @param errorMessage A mensagem de erro a ser adicionada.
     * @see ValidationHandler#append(ErrorData) Método utilizado para adicionar a mensagem de erro ao validationHandler.
     */
    private static void validate(String errorMessage) {
        validationHandler.append(new ErrorData(errorMessage));
    }

}
