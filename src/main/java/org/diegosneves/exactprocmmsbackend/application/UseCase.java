package org.diegosneves.exactprocmmsbackend.application;

public abstract class UseCase<INPUT, OUTPUT> {

    public abstract OUTPUT execute(INPUT input);

}
