json-util
=========

Simple tool to deserialize json to java objects.

Basic usage:

<pre><code>
String jsonFoobar = "{\"someValue\":123,\"fooList\":[...] ...}";
FooBar restoredFoobar = JSONUtil.get(jsonFoobar, FooBar.class);
</code></pre>

The tool is trying to associate class field names to corresponding properties in json.
Names can be either camel-case or underscore. This option can be configured.
If a field name can not be associated in this way, one can use the <code>JSONName</code>
annotation to set an explicit name for the corresponding JSON property.
If a property in JSON cannot be found, exception will be thrown. 
This can be suppressed in the configuration.
If there is no corresponding JSON property to a field name, one can use the <code>JSONIgnore</code> annotation to ignore this field.