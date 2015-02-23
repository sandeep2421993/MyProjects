package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class EventFactory {
	
	
	public static Event getEvent(byte[] marshalledBytes) throws IOException
	{
		Event returnEvent = null;
		        ByteArrayInputStream baInputStream =
				new ByteArrayInputStream(marshalledBytes);
				DataInputStream din =
				new DataInputStream(new BufferedInputStream(baInputStream));
				int type = din.readByte();
				
				switch(type)
				{
				case Protocol.OVERLAY_NODE_SENDS_REGISTRATION : returnEvent= new OverlayNodeSendsRegistration(marshalledBytes);
					break;
				case Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS :returnEvent= new RegistryReportsRegistrationStatus(marshalledBytes);
                    break;				
				case Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION : returnEvent= new OverlayNodeSendsDeregistration(marshalledBytes);
				    break;
				case Protocol.REGISTRY_REPORTS_DEREGISTRATION_STATUS: returnEvent =new RegistryReportsDeregistrationStatus(marshalledBytes);
				    break;
				case Protocol.REGISTRY_SENDS_NODE_MANIFEST:returnEvent=new RegistrySendsNodeManifest(marshalledBytes);
				    break;
				case Protocol.NODE_REPORTS_OVERLAY_SETUP_STATUS: returnEvent = new NodeReportsOverlaySetupStatus(marshalledBytes);
				    break;
				case Protocol.REGISTRY_REQUESTS_TASK_INITIATE : returnEvent= new RegistryRequestsTaskInitiate(marshalledBytes);
				    break;
				case Protocol.OVERLAY_NODE_SENDS_DATA : returnEvent= new OverlayNodeSendsData(marshalledBytes);
				    break;
				case Protocol.OVERLAY_NODE_REPORTS_TASK_FINISHED : returnEvent = new OverlayNodeReportsTaskFinished(marshalledBytes);
				    break;
				case Protocol.REGISTRY_REQUESTS_TRAFFIC_SUMMARY: returnEvent = new RegistryRequestsTrafficSummary();
				    break;
				case Protocol.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY : returnEvent = new OverlayNodeReportsTrafficSummary(marshalledBytes);
				    break;
				 default : System.out.println("improper type"); break;
				}
				
				return returnEvent;
	}

}
