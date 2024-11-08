package org.diegosneves.exactprocmmsbackend.application;

public abstract class UnitUseCase<INPUT> {

    public abstract void execute(INPUT input);

}
