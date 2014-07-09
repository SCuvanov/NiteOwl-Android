from google.appengine.ext import ndb




"""Helper model class for Imhotep API.

Defines models for persisting and querying score data on a per user basis and
provides a method for returning a 401 Unauthorized when no current user can be
determined.
"""


TIME_FORMAT_STRING = '%b %d, %Y %I:%M:%S %p'

class Event(ndb.Model):
    """Model to store events that have been inserted by users.
    """
    event_title = ndb.StringProperty(required=True)
    event_time = ndb.StringProperty(required=True)
    event_desc = ndb.StringProperty(required=True)
  
 
