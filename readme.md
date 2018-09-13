## Добро пожаловать в мир асинхронного программирования

Если ты решил углубится в язык Скала и ты хочешь больше узнать про асинхронное програмирование - то ты мой бро. Бро, я создал этот проект для того чтобы показать что асинхронное многопоточное программирование на Скала - это легко.

Начнем с монад `Future[T]` и `Observable[T]`.
 
### Latency as an Effect

By now you know that while this code looks easy, it's not as easy as you think. The reason is that reading from memory takes a lot of time. And sending a packet from Europe and back takes even more time. But this time, it's expressed in nanoseconds or microseconds which is not very intuitive for humans. So let's look at a little table that tells us the time that various operations take on a computer. Note that these times will vary because computers are changing, so take this as a rough guess. So taking a typical instruction, one nano second. But let's look at this, we're going to read from memory, so that's 250,000 nanoseconds. And sending a packet from the US to Europe takes about 150 milliseconds. And notice that a lot of these times are limited by the speed of light, so we cannot do much about them. Now I don't know about you, but for me I have no clue what 150 milliseconds means, or what 1 nanosecond means. So, let's take these numbers and take 1 nanosecond and turn that into a second, then we can calculate weeks, months, and years from that. All right, because this thing does it still, they say, read from memory, takes 50,000 nanoseconds. Who cares? All right, so let's kind of change these numbers into something that we as humans can understand. 
3:52
So one nanosecond becomes one second and then we'll turn that into days and hours. 
3:58
Now we get a much better picture. 
4:02
In terms of human timescales, reading 1 megabyte from memory takes about 3 days. 
4:12
So, it's like sending your kids to do some groceries, and it takes 3 days. They have to go on their horse to the next village, get the groceries, and come back. And of course, this can still fail. So when you're driving your horse to the next village, there might be bandits that attack you. So it doesn't only take time, it can also fail. But we dealt with failure in the previous lectures. But this one is even worse. If you look at sending a packet from the U.S. to Europe and back, in human times, that takes 5 years. 
4:52
So underneath that simple program was hidden some enormous latency, some enormous time that this computation took. And that was not visible in the type, so let's make that again visible in the type. Because we don't want this reading from memory to block for 3 years and then, for 3 days sorry. And then sending the packet to Europe and back will block for 5 years, and only continue when there's no exception. If you look in real life when we communicate with people, imagine that you would ask a question to a person, and it would take 5 years before you get an answer. You're not going to wait 5 years. What would you do? Well, what I would do is I would give this person a self addressed letter. I would say, here's a question, I know it takes 5 years for you to answer it. Once you have the answer, you just put the answer in this envelope and you send it back to me. 
5:56
That idea in computer science is called a callback. And that's exactly the mechanism we're going to use. So if a computation takes a lot of time, we're going to introduce a type 
6:10
where you can register a call back that will be invoked once this computation terminates. 
6:21
And again, that computation might terminate successfully or with an exception. 
6:28
But let's first show that we as humans are still superior to machines. There's now a lot of talk about the rise of AI, and that we have to be really scared of computers. But I don't think that's that bad. Because apparently somebody has swum from 
6:52
Europe to the U.S, and that took about 3 months and if you want to walk across the continental U.S. that takes about 12 months. So if you're going to make the same journey that that packet makes, 
7:09
it means that humans are still twice as fast as computers, so I wouldn't worry too much for the machines take over, all right. But here's the question. Is there a monad that we can use to 
7:24
express the fact that computations take time. 
7:28
Well, after the break. 

* [???](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/readmes/first_page.md)


![alt text](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/images/timings_on_tipical_ops_on_typical_pc.png "time of ops")

![alt text](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/images/obs.png "obs")


Для каждого примера существует тест.
