'''
Created on Nov 4, 2013

@author: Sean
'''

"""ProtoRPC message class definitions for Imhotep API."""



from protorpc import messages

class EventMessage(messages.Message):
    """ProtoRPC message definition to represent an event."""
    event_title = messages.StringField(1)
    event_time = messages.StringField(2)
    event_desc = messages.StringField(3)
    

class EventRequest(messages.Message):
    """ProtoRPC message definition to represent an event to be inserted."""
    event_title = messages.StringField(1)
    event_time = messages.StringField(2)
    event_desc = messages.StringField(3)

class EventResponse(messages.Message):
    """ProtoRPC message definition to represent an event that is stored."""
    event_title = messages.StringField(1)
    event_time = messages.StringField(2)
    event_desc = messages.StringField(3)
