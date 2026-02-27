package com.example.graphql.config;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class GraphQlExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof NoSuchElementException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage())
                    .errorType(graphql.ErrorType.DataFetchingException)
                    .build();
        }

        return null;
    }
}
