package pk.smartq.journalApp.providers;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import pk.smartq.journalApp.entities.User;
import java.util.List;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
            Arguments.of(User.builder().username("osama").password("toor").roles(List.of("USER")).build()),
            Arguments.of(User.builder().username("falak").password("toor2").roles(List.of("USER")).build()),
            Arguments.of(User.builder().username("admin").password("admin").roles(List.of("ADMIN")).build()),
            Arguments.of(User.builder().username("hanood").password("toor3").roles(List.of("USER")).build())
            );
    }
}