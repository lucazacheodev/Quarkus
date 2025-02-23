package util;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
    info = @Info(
        title = "Fruit API", 
        version = "1.0.1", 
        contact = @Contact(
            name = "Api Support",
            url = "http://exampleurl.com/contact", 
            email = "techsupport@example.com"), 
        license = @License(
            name = "Apache 2.0", 
            url = "https://www.apache.org/licenses/LICENSE-2.0.html")))

public class ApplicationInfo extends Application {

}
