package resources;

public enum APIResources {
	iTunesSearchAPI("/search"),
	iTunesLookupAPI("/lookup");	
	
	private String resource;
	
	APIResources(String resource)
	{
		this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}
}
