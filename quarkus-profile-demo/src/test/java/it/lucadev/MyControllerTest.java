package it.lucadev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@QuarkusTest
class MyControllerTest {

    @Mock
    MyRepository mockRepository;

    @Inject
    MyController myController;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(MyRepository.class);
    }

    @Test
    void testGetEntity() {
        when(mockRepository.findById(1L)).thenReturn(null);

        Response response = myController.get(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(null, response.getEntity());
    }
}
