package org.diegosneves.exactprocmmsbackend.domain.validation.handler;

import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.Validation;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;

import java.util.List;

/**
 * A classe {@link ThrowsValidationHandler} é uma implementação da interface {@link ValidationHandler}.
 * Essa classe é responsável por executar validações e lançar exceções em caso de erros
 * durante o processo de validação. Utiliza a exceção {@link DomainException} para encapsular
 * os erros de validação.
 *
 * <p>Métodos principais:</p>
 * <ul>
 *     <li><code>append(ErrorData anError)</code>: Adiciona um erro de validação e lança uma <code>DomainException</code>.</li>
 *     <li><code>append(ValidationHandler aHandler)</code>: Adiciona os erros de outro handler de validação e lança uma <code>DomainException</code>.</li>
 *     <li><code>validate(Validation aValidation)</code>: Executa uma operação de validação e lança uma <code>DomainException</code> caso ocorra uma exceção.</li>
 *     <li><code>getErrors()</code>: Retorna uma lista de erros.</li>
 * </ul>
 * <p>
 * Esta classe é útil para cenários onde a validação deve interromper o fluxo de execução
 * imediatamente ao encontrar um erro, lançando uma exceção apropriada.
 *
 * @author diegoneves
 * @since 1.0.0
 * @see ValidationHandler
 */
public class ThrowsValidationHandler implements ValidationHandler {

    @Override
    public ValidationHandler append(final ErrorData anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(final ValidationHandler aHandler) {
        throw DomainException.with(aHandler.getErrors());
    }

    @Override
    public ValidationHandler validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (Exception e) {
            throw DomainException.with(new ErrorData(e.getMessage()));
        }
        return this;
    }

    @Override
    public List<ErrorData> getErrors() {
        return List.of();
    }

}
