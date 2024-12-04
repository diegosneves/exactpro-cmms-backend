package org.diegosneves.exactprocmmsbackend.application.client.update;

import io.vavr.control.Either;
import org.diegosneves.exactprocmmsbackend.application.UseCase;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.Notification;

public abstract class UpdateClientUseCase extends UseCase<UpdateClientCommand, Either<Notification, UpdateClientOutput>> {

}
