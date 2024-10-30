package org.diegosneves.exactprocmmsbackend.infrastructure.configuration.web;

import lombok.extern.slf4j.Slf4j;
import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * A classe {@link GlobalExceptionHandler} é um manipulador de exceções global para controladores.
 * Ela lida com as exceções lançadas durante o processamento de solicitações e gera respostas de erro apropriadas.
 * A classe é anotada com {@link RestControllerAdvice} para aplicar o tratamento de exceção globalmente
 * a todas as classes de controlador.
 *
 * @author diegosneves
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Manipula exceções gerais e retorna uma resposta de erro apropriada.
     *
     * @param exception A exceção que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link DomainException} com a mensagem da exceção e um código de status HTTP
     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<DomainException> handleFailures(Exception exception) {
//        DomainException dto = new DomainException(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
//    }

    /**
     * Esta é uma função que lida com exceções do tipo {@link HttpMessageNotReadableException} em todo o controlador.
     * Um objeto {@link HttpMessageNotReadableException} é lançado quando há um erro de sintaxe no corpo HTTP da solicitação.
     * Este método captura essa exceção, registra uma mensagem de erro, cria um objeto {@link DomainException}
     * contendo essa mensagem e um valor de retorno HTTP de {@code BAD_REQUEST} (400) e, em seguida, retorna essa entidade.
     *
     * @param exception A exceção {@link HttpMessageNotReadableException} que foi lançada quando ocorreu um erro de sintaxe no corpo HTTP de uma solicitação.
     * @return Uma nova ResponseEntity contendo um objeto ExceptionDTO com a mensagem de erro e o status {@code BAD_REQUEST}.
     * O valor de HttpStatus para {@code BAD_REQUEST} é 400, o que indica que a solicitação era inválida ou não pôde ser entendida pelo servidor.
     * @apiNote {@link HttpMessageNotReadableException} Esta exceção é lançada quando ocorre um erro de sintaxe no corpo HTTP da solicitação, o que significa que a solicitação não pode ser lida.
     */
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<DomainException> handleJSONFailures(HttpMessageNotReadableException exception) {
//        String message = "Não foi possível processar o conteúdo da solicitação. Por favor, confira se os dados foram inseridos corretamente.";
////        DomainException dto = new DomainException(message, HttpStatus.BAD_REQUEST.value());
//        log.error(exception.getMessage(), exception);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
//    }


}
