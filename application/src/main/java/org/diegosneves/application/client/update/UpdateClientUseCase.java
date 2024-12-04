package org.diegosneves.application.client.update;

import io.vavr.control.Either;
import org.diegosneves.application.UseCase;
import org.diegosneves.domain.validation.handler.Notification;

public abstract class UpdateClientUseCase extends UseCase<UpdateClientCommand, Either<Notification, UpdateClientOutput>> {

}
