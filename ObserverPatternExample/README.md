Observer Pattern Example
========================

The MainActivity hosts a stopwatch
the stop watch ticks every second
observer pattern is used for the stop watch to inform about activity of the tick events
- The MainActivity implements an interface with the required methods it wants to know from the StopWatch
- The MainActivity registers itself in the StopWatch
- The StopWatch keeps a reference to the registered listener and invokes its method in every tick event
