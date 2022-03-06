package io.floodplain.movie

import io.smallrye.mutiny.Multi
import io.vertx.mutiny.pgclient.PgPool
import io.vertx.mutiny.sqlclient.Row
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


data class Customer(val customerId: Int,val firstName: String, val lastName: String, val email: String)

@ApplicationScoped
@Path("/customers")
class CustomerService {

    @Inject
    lateinit var client: PgPool

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun customers(): Multi<Customer> {
        return client.query("SELECT * from customer")
            .execute()
            .toMulti()
            .flatMap { a->a.toMulti() }
            .map { customerFromRow(it) }
    }
}

fun customerFromRow(row: Row) =
    Customer(row.getInteger("customer_id"),row.getString("first_name"),row.getString("last_name"),row.getString("email"))
