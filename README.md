json-util
=========

Simple tool to deserialize json to objects.

Usage:

<pre><code>
String jsonFoobar = "{...";
FooBar restoredFoobar = JSONUtil.get(jsonFoobar, FooBar.class);
</code></pre>

ToDo:

Right now only basic java types are supported