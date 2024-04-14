OCaml

type comparing = GT | EQ | LT;;

module type SOMECOMPARATOR =
sig
	type t
	val compare: t option -> t option-> comparing
end;;

module SomeIntComparator : SOMECOMPARATOR with type t = int =
struct

	type t = int
	let compare o1 o2 = 
		match (o1, o2) with
		| (Some x, Some y) -> begin
								if o1 < o2 then LT 
								else if o1 > o2 then GT 
								else EQ
							  end
		| (_, _) -> failwith "Illegal argument"
	
end;;

module PriorityQueueImpl (SCmp : SOMECOMPARATOR) =
struct
	type element = SCmp.t
	type q = { mutable queue : element option array; mutable s : int; mutable c : int }
	
	exception EmptyQueue of string
	
	let size = 5
	
    let create() = { queue = (Array.make size None); s = 0; c = size }

		
	let isEmpty pq = pq.s = 0
		
	let swap (left, right, pq) = 
		if left != right then begin let temp = pq.queue.(left) in
										pq.queue.(left) <- pq.queue.(right);
										pq.queue.(right) <- temp
							  end	
	
	let increase pq = pq.queue <- (Array.append pq.queue (Array.make size None));
					  pq.c <- pq.c + size
	
	
	let insert(newElem, pq) = begin if pq.s = pq.c then increase pq;
								pq.queue.(pq.s) <- Some newElem;
								pq.s <- succ pq.s;
								if(pq.s > 1) then begin
										let child = ref (pq.s - 1) in
										while (!child >= 1 && (SCmp.compare  pq.queue.(!child) pq.queue.((!child - 1)/2)) = GT) do 
											swap(!child, (!child - 1)/2, pq);
											child := (!child - 1)/2
										done; 
									end
							  end; pq
	
	let rec repair (pq, index) =
		if index < pq.s then begin
			let left = 2 * index + 1 in
			if left < pq.s then begin
				let right = 2 * index + 2 in	
				
				if right >= pq.s then begin if (SCmp.compare pq.queue.(index) pq.queue.(left))  = LT then begin swap(index, left, pq); repair(pq,left) end end

				else if (SCmp.compare pq.queue.(left) pq.queue.(right) ) = GT then begin if (SCmp.compare  pq.queue.(index) pq.queue.(left) ) = LT then begin swap(index, left, pq); repair(pq, left) end end

				else if (SCmp.compare pq.queue.(index) pq.queue.(right) ) = LT then begin swap(index, right, pq); repair(pq, right) end

			end
		end

	let get elem = 
		match elem with
		| Some x -> x 
		| None   -> failwith "None" 
	
	let top pq = if (isEmpty pq) then raise (EmptyQueue "Priority Queue is EMPTY!!!")
				 else (get pq.queue.(0))

				 
	let retrive pq = if (isEmpty pq) then raise (EmptyQueue "Priority Queue is EMPTY!!!")
					 else begin let result = get (pq.queue.(0)) in
								pq.queue.(0) <- None;
								pq.s <- pred pq.s;
								swap(0, pq.s, pq);
								repair(pq, 0);
								result
						  end

end;;	


module IntQueue = PriorityQueueImpl(SomeIntComparator);;

let queueInt = IntQueue.create();;

IntQueue.insert(1, queueInt);;
 
IntQueue.insert(2, queueInt);; 

IntQueue.insert(3, queueInt);; 

IntQueue.insert(4, queueInt);;
 
IntQueue.insert(5, queueInt);; 

IntQueue.insert(6, queueInt);; 



IntQueue.retrive(queueInt);;

IntQueue.retrive(queueInt);;

IntQueue.retrive(queueInt);;

IntQueue.retrive(queueInt);;

IntQueue.retrive(queueInt);;

IntQueue.retrive(queueInt);;


module type PRIORITYQUEUE =
	functor (SCmp: SOMECOMPARATOR) ->
		sig
			type element = SCmp.t
			type q
			exception EmptyQueue of string 
			val create: unit -> q
			val insert: element * q -> q
			val retrive: q -> element
		end;;


module PriorityQueue = (PriorityQueueImpl: PRIORITYQUEUE);;

module IntPriorityQueue = PriorityQueue(SomeIntComparator);;

let q = IntPriorityQueue.create();;

IntPriorityQueue.insert(1, q);;

IntPriorityQueue.insert(2, q);; 

IntPriorityQueue.insert(3, q);; 

IntPriorityQueue.insert(4, q);;
 
IntPriorityQueue.insert(5, q);; 

IntPriorityQueue.insert(6, q);; 



IntPriorityQueue.retrive(q);;

IntPriorityQueue.retrive(q);;

IntPriorityQueue.retrive(q);;

IntPriorityQueue.retrive(q);;

IntPriorityQueue.retrive(q);;

IntPriorityQueue.retrive(q);;