# The Golden Crown
A geek trust problem solved using clojure. I have tried to solutionize the problem as a production app which can be extended easily in the future if required. Example if there are multiple rulers, more kingdoms, auditlog of messages need to be there etc.

## Entities
### Kingdom
There are six kingdoms that are created as the inital setup, each of which will have an `emblem` and an `animal` associated with it. Kingdom entity will receive a message from the domain which will be decoded to figure out if it wants to be an ally to king shan.

### Message
For every message that is receieved from the cli a message entity will be created. A very crude language processing will be done on it to determine its properties. This entity will contain the `kingdom-name`, `type`, `subtype` and the `message` for that particular kingdom.
Messages will have a type based on whether its a `question` or an `action`. `question` will have no kingdom.
#### Question Message
`question` messages will be determined by a very crude language processing, essentially checking for certain keywords. The questions will have a subtype, which will be one of these values `:who-is-ruler?` `:allies-of-ruler?`
#### Action Message
`action` messages will be used to figure whether the kingdom will become an ally to the aspiring ruler.

## Domain
The core logic of the problem is going to be dealt here. It will receive a message from the CLI which will be converted to a message entity. If the type is a `:question` we'll either forward the message to the kingdom to process or if the subtype is `:who-is-ruler?` we'll loop over each kingdom and figure out if king shan became a ruler or not. Or if the subtype is `:allies-of-ruler?` we'll loop over eack kingdom and figure out who all are allies of king shan.

## Gateway
### CLI
This will be initalized by the core to start listening to `stdin`. It will read each line from stdin and pass it on to the domain. The domain will send back a reply for each `question message`. The reply will be a string, which the cli will output.

## Persistence
### Memory
#### Kingdom
We will persist the kindom data in the memory as a list. Entity will be interacting with this layer.
