package org.diegosneves.exactprocmmsbackend.application.client.create;

import io.vavr.control.Either;
import org.diegosneves.exactprocmmsbackend.application.UseCase;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.Notification;

public abstract class CreateClientUseCase extends UseCase<CreateClientCommand, Either<Notification,CreateClientOutput>> {


}
