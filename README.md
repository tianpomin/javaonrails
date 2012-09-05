javaonrails
===========

java application, which is developed with this lib, could be deployed as modules which could be loaded, run, updated and removed dynamically at runtime, but it is lighter and easier than osgi. 
you can develop a java project with it, just like to develope a common java project with struts spring and hibernate.

one moudule may has several resources.
each resource has five accessible methods, create delete update retrive and option. 
to access a moudule by method create, a new record will be created.
to access a moudule by method delete, the specified record will be deleted. 
to access a moudule by method option, the explaination about this moudle will be returned.

a resource could be accessed by a resource name which format looks like a url string. 

for example, there is a moudle named unittest which could manage unit test cases. 
the test case url likes "/unittest/testcase", and parameter to access by method retrive could be :{"method":"retrive","id":1}.
if you want to execute some test case, you can define a resource named "/unittest/testcase/execution", and to access by method create. 
all resources must be defined with a name, and must be named with noun words.

it supports various of format for parameter and response, like json, http request string, plain text, java object, base64 string, byte array, and so on.
you can also specify other format by implementing some interfaces.

based on the thought, you can build a moudle repository, and the moudles could also be retrived, loaded, executed and managed remotely and localy in use of this lib.

every one could download, use and modify it freely, and welcome to develop this lib with me.

first period, to publish a version with a micro core, depending on ioc framework spring.
second period, to publish a version depending on an ioc framework, which is implemented independently.

at last, this project is modified with eclipse.

======================================================================================================================================================
SEP 5 2012
classes in directory meJOR has been tested in my project, except classes on nio and me.jor.pool.ThreadPool.
these classes could help you to reduce development duplication of work in use of struts2 and hibernate. 
also, there are so many small utilities to reduce duplication of work in use of string, thread, collections and so on. they will help you, i believe.

classes in directory meROA provide the module framework, however, this framework could not work yet, i'm afraid, because of some defects in design and lack of test. i'll reconsitution this framework, in the next few weeks.

at last, i should explain that, due to more than a dozon hours of work, i have few time to finish it.