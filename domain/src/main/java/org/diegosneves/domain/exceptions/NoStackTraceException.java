package org.diegosneves.domain.exceptions;

/**
 * A exceção {@link NoStackTraceException} é uma especialização de {@link RuntimeException}.
 * Ela é usada para lançar exceções onde a geração de um stack trace (rastreamento da pilha de chamadas)
 * é desnecessária ou indesejada, ajudando a melhorar a performance e reduzir o uso de recursos.
 * <p>
 * Essa classe é particularmente útil em situações onde a captura do stack trace
 * não adiciona valor significativo ou em sistemas de alto desempenho onde a sobrecarga
 * de gerar e armazenar o stack trace precisa ser minimizada.
 * <p>
 * Esta exceção pode ser instanciada com apenas uma mensagem de erro ou com uma
 * mensagem de erro juntamente com outra exceção que foi a causa.
 *
 * @author diegoneves
 * @see RuntimeException
 * @since 1.0.0
 */
public class NoStackTraceException extends RuntimeException {

    /**
     * Constrói uma nova instância de <code>NoStackTraceException</code> com a mensagem de detalhe fornecida.
     * Nenhuma causa (exceção) é fornecida.
     *
     * @param message a mensagem de detalhe que descreve a exceção.
     */
    public NoStackTraceException(String message) {
        this(message, null);
    }

    /**
     * Constrói uma nova instância de <code>NoStackTraceException</code> com a mensagem de detalhe e causa fornecidas.
     *
     * @param message a mensagem de detalhe que descreve a exceção.
     * @param cause   a causa da exceção (pode ser <code>null</code>).
     */
    public NoStackTraceException(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
