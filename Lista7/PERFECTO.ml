OCaml

type comparing = GT | EQ | LT;;

module type COMPARATOR =
sig
	type t
	val compare: t -> t -> comparing
end;;


module IntComparator : COMPARATOR with type t = int =
struct

	type t = int
	let compare o1 o2 = if o1 < o2 then LT 
						else if o1 > o2 then GT 
						else EQ
	
end;;

module FloatComparator : COMPARATOR with type t = float =
struct

	type t = float
	let compare o1 o2 = if o1 < o2 then LT
						else if o1 > o2 then GT 
						else EQ
	
end;;

module StringComparator : COMPARATOR with type t = string =
struct

	type t = string
	let compare o1 o2 = let result = (String.compare o1 o2) in
						if result < 0 then LT
						else if result > 0 then GT 
						else EQ
	
end;;

let cmp1 = IntComparator.compare 1 2;;
let cmp11 = IntComparator.compare 1 1;;
let cmp111 = IntComparator.compare 2 1;;
let cmp2 = FloatComparator.compare 2. (-2.);;
let cmp22 = FloatComparator.compare 2. 2.;;
let cmp223 = FloatComparator.compare (-2.) 2.;;
let cmp3 = StringComparator.compare "KOT" "PIES";;
let cmp33 = StringComparator.compare "kot" "KOT";;
let cmp33 = StringComparator.compare "PIES" "KOT";;


module PriorityQueueImpl (Cmp : COMPARATOR) =
struct
	type element = Cmp.t
	type q = { mutable queue : element option array; mutable s : int; mutable c : int }
	
	exception EmptyQueue of string
	
	let size = 5
	
    let create () = { queue = (Array.make size None); s = 0; c = size }
	
	let get elem = 
		match elem with
		| Some x -> x 
		| None   -> failwith "None" 
		
	let compare elem1 elem2 = 
		match (elem1, elem2) with
		| (Some x, Some y) -> Cmp.compare x y
		| (_, _) 		   -> failwith "Illegal argument"
		
		
	let swap (left, right, pq) = 
		if left != right then begin let temp = pq.queue.(left) in
										pq.queue.(left) <- pq.queue.(right);
										pq.queue.(right) <- temp
							  end

	let isEmpty pq = pq.s = 0
	
	let increase pq = pq.queue <- (Array.append pq.queue (Array.make size None));
					  pq.c <- pq.c + size
	
	
	let insert(newElem, pq) = begin if pq.s = pq.c then increase pq;
								pq.queue.(pq.s) <- Some newElem;
								pq.s <- succ pq.s;
								if(pq.s > 1) then begin
										let child = ref (pq.s - 1) in
										while (!child >= 1 && (compare pq.queue.(!child) pq.queue.((!child - 1)/2)) = GT) do 
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
				
				if right >= pq.s then begin if (compare pq.queue.(index) pq.queue.(left) ) = LT then ( swap(index, left, pq); repair(pq, left) ) end

				else if (compare pq.queue.(left) pq.queue.(right) ) = GT then begin if (compare pq.queue.(index) pq.queue.(left) ) = LT then ( swap(index, left, pq); repair(pq, left) ) end

				else if (compare pq.queue.(index) pq.queue.(right) ) = LT then ( swap(index, right, pq); repair(pq, right) )

			end
		end

				 
	let retrive pq = if (isEmpty pq) then raise (EmptyQueue "Priority Queue is EMPTY!!!")
					 else begin let result = (get pq.queue.(0)) in
								pq.queue.(0) <- None;
								pq.s <- pred pq.s;
								swap(0, pq.s, pq);
								repair(pq, 0);
								result
						  end

end;;	


module IntQueue = PriorityQueueImpl(IntComparator);;

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
	functor (Cmp: COMPARATOR) ->
		sig
			type element = Cmp.t
			type q
			exception EmptyQueue of string 
			val create: unit -> q
			val insert: element * q -> q
			val retrive: q -> element
		end;;


module PriorityQueue = (PriorityQueueImpl: PRIORITYQUEUE);;

module IntPriorityQueue = PriorityQueue(IntComparator);;

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

IntPriorityQueue.retrive(q);;

module FloatPriorityQueue = PriorityQueue(FloatComparator);;

let qf = FloatPriorityQueue.create();;

FloatPriorityQueue.insert(-1., qf);;

FloatPriorityQueue.insert(-2., qf);; 

FloatPriorityQueue.insert(-3., qf);; 

FloatPriorityQueue.insert(-4., qf);;
 
FloatPriorityQueue.insert(-5., qf);; 

FloatPriorityQueue.insert(-6., qf);; 


FloatPriorityQueue.retrive(qf);;

FloatPriorityQueue.retrive(qf);;

FloatPriorityQueue.retrive(qf);;

FloatPriorityQueue.retrive(qf);;

FloatPriorityQueue.retrive(qf);;

FloatPriorityQueue.retrive(qf);;

FloatPriorityQueue.retrive(qf);;



























	let top pq = if (isEmpty pq) then raise (EmptyQueue "Priority Queue is EMPTY!!!")
				 else (get pq.queue.(0))
	
	
