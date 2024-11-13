package org.diegosneves.exactprocmmsbackend.application.client.retrieve.list;

import org.diegosneves.exactprocmmsbackend.application.UseCase;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientSearchQuery;
import org.diegosneves.exactprocmmsbackend.domain.pagination.Pagination;

public abstract class ListClientsUseCase extends UseCase<ClientSearchQuery, Pagination<ClientListOutput>> {

}
