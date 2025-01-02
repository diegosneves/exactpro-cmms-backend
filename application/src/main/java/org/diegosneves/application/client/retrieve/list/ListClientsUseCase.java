package org.diegosneves.application.client.retrieve.list;

import org.diegosneves.application.UseCase;
import org.diegosneves.domain.client.ClientSearchQuery;
import org.diegosneves.domain.pagination.Pagination;

public abstract class ListClientsUseCase extends UseCase<ClientSearchQuery, Pagination<ClientListOutput>> {

}
