# Multiple sleeping barber problem

### Problem description:
A barber shop which has multiple barbers working in it, when a barber is not working, he sleeps till a customer arrive, when a customer arrive a barber wakes up, cuts his hair then check whether there are more waiting customers, if there is none he goes back to sleep.  The other barbers stay asleep as well if there are no more customers for them to work.
When a customer arrives, he checks if there’s an empty chair for him, if there’s none he leaves else he awakes a sleeping barber to cut his hair.

### An example of deadlock:
When barbers wait for their customers and at the same time the customers wait for the barbers to call out their names.
#### Solution:
The barbers should check the customer’s waiting room before sleep

### An example of starvation:
When customers don’t follow an order and customers get a haircut before others even if they wait for a long time, or when a barber keep doing all the jobs leaving others sleeping.
#### Solution:
Giving higher priority to the customer or barber who waited more by enabling fairness in semaphore or lock

### Real-world Example:	
A program to check whether the solution of a problem is correct or not by having a tester thread that checks whether the answer is the same as the correct answer or not, and a submission thread that loads the answer. The submission waits in a queue for a tester to finish working, and testers wait for a submission to arrive to test it like how the barber wait customer and a customer wait for barber.
 
## Solution  Pseudocode:
```
# We use a Semaphores to check whether a Barber or a customer is ready
class BarberShop(chairs, barbers)
    #Initialize locks and Semaphores, Fairness = true to avoid starvation
    Semaphore waiting_customers ← Semaphore(0, true)
    Semaphore barbers_ready ← Semaphore(barbers, true)
    customer_lock ← Lock(true)
    
    number_of_customer ← 0
    
    Function add_customer()
        lock customer_lock
        if number_of_customer = chairs
            print(shopFull) 
            unlock customer_lock
            Return
        Else
            increment number_of_customer
            unlock customer_lock
            increment/release waiting_customers
            #Check whether a barber is ready by acquiring Semaphore
            aquire barbers_ready
            #Check whether there's a waiting customer by aquiring Semaphore (useless)
            aquire waiting_customers
            customer.getHairCut()
            barber.Cuthair()
            Wait for barber and customer to finish
            release barbers_ready
```
