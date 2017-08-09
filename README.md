We technically don't need this plugin anymore, since we store the settings as
`local storage` in the usercache, and can directly access it from both native
code and javascript. BUT the server communication currently does not use the
usercache, and there might be places that still use this code (e.g. to display
the server that we are connected to) for debugging.

This also provides a convenient place to provide the format for the user settings.

So let's extend this plugin for now, but soon we should remove it and use the
JSON directly.
