package emailforensics;

import java.io.File;
import java.io.IOException;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;

public class GetLocation {
	static Location location;
	public GetLocation(String ip) throws IOException
	{
		
		File dbfile = new File("GeoLiteCity.dat");
		LookupService lookupService = new LookupService(dbfile, LookupService.GEOIP_MEMORY_CACHE);
         location = lookupService.getLocation(ip);        //System.out.println(location);
		// Populate region. Note that regionName is a MaxMind class, not an instance variable
		if (location != null) {
		    location.region = regionName.regionNameByCode(location.countryCode, location.region);
		System.out.println(location.countryName);
		System.out.println(location.city);
		System.out.println(location.latitude);
		System.out.println(location.longitude);
		}
	}
	
	public static String getCountry()
	{
		return location.countryName;
	}
	
	public static String getCity()
	{
		return location.city;
	}
	public static String getRegion()
	{
		return location.region;
	}
	
	public static float getLatitude()
	{
		return location.latitude;
	}
	
	public static float getLongitude()
	{
		return location.longitude;
	}
}
