
import endpoints
from protorpc import remote

from models import Event
from imhotep_api_messages import EventRequest
from imhotep_api_messages import EventResponse


WEB_CLIENT_ID = '395015761507-epj8rafk5km9s4jsmad4lk9b3tkk5u51.apps.googleusercontent.com'
ANDROID_CLIENT_ID = '395015761507-fp7t7bb2a4n6erseq1k38r5scah476ir.apps.googleusercontent.com'
IOS_CLIENT_ID = 'replace this with your iOS client ID'
ANDROID_AUDIENCE = WEB_CLIENT_ID

@endpoints.api(name='imhotep', version='v1',
               description='Endpoints API for Imhotep',
               hostname='orbital-alpha-888.appspot.com',
               allowed_client_ids=[WEB_CLIENT_ID, ANDROID_CLIENT_ID, endpoints.API_EXPLORER_CLIENT_ID],
               scopes=['https://www.googleapis.com/auth/userinfo.email','https://www.googleapis.com/auth/datastore'])

class ImhotepApi(remote.Service):
    """Class which defines Imhotep API v1."""
       
    
    @endpoints.method(EventRequest, EventResponse, path="events",
                      http_method='POST', name='events.insert') 
    
    def event_insert(self, request):
        """
        Insert a new event item for the current user.
        
        Args:
            request: An instance of EventRequestMessage parsed from the API request.
        """
        entity = Event(event_title=request.event_title, event_desc=request.event_desc, event_time=request.event_time)
        entity.put()
           
       
    
APPLICATION = endpoints.api_server([ImhotepApi], restricted=False)
