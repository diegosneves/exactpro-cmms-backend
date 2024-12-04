package org.diegosneves.application.client.create;

import io.vavr.control.Either;
import org.diegosneves.application.UseCase;
import org.diegosneves.domain.validation.handler.Notification;

public abstract class CreateClientUseCase extends UseCase<CreateClientCommand, Either<Notification,CreateClientOutput>> {


}
