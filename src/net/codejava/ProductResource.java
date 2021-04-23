//https://www.codejava.net/java-ee/web-services/java-crud-restful-web-services-examples-with-jersey-and-tomcat


//https://github.com/skalpa13/webService2020

package net.codejava;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/products")
public class ProductResource {
	private ProductDAO dao = ProductDAO.getInstance();
	
	@GET //http://localhost:8080/restfulWebServiceCrudList/rest/products
	@Produces(MediaType.APPLICATION_XML)
	public List<Product> list() {
		return dao.listAll();
	}
	
	@GET
	@Path("{id}") //http://localhost:8080/restfulWebServiceCrudList/rest/products/1
	@Produces(MediaType.APPLICATION_XML)
	public Response searchProdById(@PathParam("id") int id) {
		Product product = dao.get(id);
		if (product != null) {
			return Response.ok(product, MediaType.APPLICATION_XML).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	
	
	
	
	// source https://o7planning.org/fr/11203/restclient-un-debogueur-pour-restful-web-service
	//Pour spécifier le type de données renvoyées, vous devez rédiger une application Client pour former des Requêtes (Request) personnalisées 
	//et les envoyer au service web. 
	//Toutefois, vous pouvez également utiliser les AddOns du navigateur pour ajuster les requêtes avant de les envoyer au service RESTful. 
	
	//Le  RESTClient est un outil de débogage qui est mis en place pour les navigateurs pour vous permet de personnaliser les requêtes envoyées à un service RESTful. Il aide les programmeurs à développer l'application de test RESTful Service pour leurs services. 
	//sur firefox install add-ons "rested client"  moz-extension://06b79583-1ab3-45fb-afaf-b7c0238a388a/dist/index.html
	//https://addons.mozilla.org/fr/firefox/addon/rester/
	//icone apparait dans barre d'outils sur la droite 
	//permet de tester le detele , post , put
//	Allow: GET, POST, HEAD
		@DELETE // http://localhost:8080/restfulWebServiceCrudList/rest/products/2
		@Path("{id}")
		public Response delete(@PathParam("id") int id) {
			if (dao.delete(id)) {
				return Response.ok().build();					
			} else {
				return Response.notModified().build();
			}
		}
	
	//post -> crud create  http://localhost:8080/restfulWebServiceCrudList/rest/products (à insérer dans  add on mozilla url rested client )
	/*in the body
		{
		    "name": "PRODUCT API",
		    "price": 12
		}*/
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Product product) throws URISyntaxException {
		System.out.println("name: " +product.getName());
		int newProductId = dao.add(product);
		URI uri = new URI("/products/" + newProductId);
		System.out.println("uri: " +uri);
		return Response.created(uri).build();
	}
	
	
	
	
	
	
	@POST // http://localhost:8080/restfulWebServiceCrudList/rest/products/nomprod/12.45  
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{name}/{price}")
	public Response add( @PathParam("name") String name,	@PathParam("price") float price) throws URISyntaxException
	{
		Product product = new Product();
		product.setName(name);
	    product.setPrice(price);
	    int newProductId = dao.add(product);
		URI uri = new URI("/products/" + newProductId);
		System.out.println("uri: " +uri);
			
	    return Response.created(uri).build();
	}


	
	//put -> crud update http://localhost:8080/restfulWebServiceCrudList/rest/products/4
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Product product) {
		product.setId(id);
		if (dao.update(product)) {
			return Response.ok().build();
		} else {
			return Response.notModified().build();
		}
	}
	
	
	
}
