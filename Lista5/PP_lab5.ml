ZADANIE 1

OCaml

type 'a llist = LNil | LCons of 'a * (unit -> 'a llist);;


let rec lfrom k = LCons (k, function() -> lfrom (k+1));;

let rec ltake (n, lxs) =
	match (n, lxs) with
	| (0, _ )          -> []
	| (_, LNil) 	   -> []
	| (n, LCons(x,xf)) -> x :: ltake(n-1, xf());; 
	
let rec ltake2 (n, (lxs, rxs)) = (ltake (n, lxs), ltake (n, rxs));; 
	
let rec toLazyList xs =
	match xs with 
	| []   -> LNil
	| h::t -> LCons(h, function () -> toLazyList t);;
	
let lhd = function
	| LNil 		   -> failwith "lhd"
	| LCons (x, _) -> x;;

let ltl = function
	| LNil 			-> failwith "ltl"
	| LCons (_, xf) -> xf();;

let lfib = 

    let rec lfibIn(p, n) = LCons(p+n, function () -> (lfibIn(n, p+n))) in
	
    LCons(0, function () -> (LCons(1, function () -> (lfibIn(0, 1)))));;


let lsplit lazylist =
	let rec takeSecondElement lazylist2 = 
		match lazylist2 with
		| LNil 							 -> LNil
		| LCons(x, xf) when xf() <> LNil -> LCons(x, function () -> takeSecondElement(ltl(xf())))
		| LCons(x, xf)                   -> LCons(x, function () -> LNil)
	
	in match lazylist with
		| LNil 		   -> (LNil, LNil)
		| LCons(_, xf) -> (takeSecondElement lazylist, takeSecondElement (xf()));;
		
let lsplit lazylist =
	let rec takeSecondElement lazylist2 = 
		match lazylist2 with
		| LNil 		    -> LNil
		| LCons(x, xf)  -> let tail = xf() in if tail <> LNil then LCons(x, function () -> takeSecondElement(ltl(tail))) else LCons(x, function () -> LNil)

	
	in match lazylist with
		| LNil 		   -> (LNil, LNil)
		| LCons(_, xf) -> (takeSecondElement lazylist, takeSecondElement (xf()));;

lsplit LNil;;

lsplit lfib;;

lsplit (toLazyList [1]);;

ltake2(10, lsplit (lfrom 0));;

ltake2(10, lsplit lfib);;

ltake2(10, lsplit (toLazyList [10;9;8;7;6;5;4;3;2;1]));;

ltake2(10, lsplit (toLazyList [10;9;8;7;6;5;4;3;2;1;0]));;

ZADANIE 2

OCaml

type 'a llist = LNil | LCons of 'a * 'a llist Lazy.t;;


let rec lfrom k = LCons (k, lazy (lfrom (k+1)));;

let rec ltake (n, lxs) =
	match (n, lxs) with
	| (0, _ )          	     -> []
	| (_, LNil) 	   		 -> []
	| (n, LCons(x, lazy xf)) -> x :: ltake(n-1, xf);; 
	
	
let rec ltake2 (n, (lxs, rxs)) = (ltake (n, lxs), ltake (n, rxs));; 

let rec toLazyList xs =
	match xs with 
	| []   -> LNil
	| h::t -> LCons(h, lazy(toLazyList t));;
	

let rec lcombine llst rlst f =
	match(llst, rlst) with
	| (LNil, LNil)                     			 -> LNil
	| (LNil, LCons(h2, lazy xf2))           	 -> LCons(h2, lazy xf2)
	| (LCons(h1, lazy xf1), LNil) 		   	     -> LCons(h1, lazy xf1)
	| (LCons(h1, lazy xf1), LCons(h2, lazy xf2)) -> LCons(f h1 h2, lazy (lcombine xf1 xf2 f));;
	
	 

let lxs1 = toLazyList [1;2;3;4;5;6;7;8;9;10];;	
let lxs2 = toLazyList [10;9;8;7;6;5;4;3;2;1];;

lcombine LNil LNil (+);;

ltake(5, (lcombine LNil lxs1 (+)));;
	
ltake(5, (lcombine lxs2 LNil (+)));;

ltake(5, (lcombine lxs1 lxs2 (+)));;

ltake(5, (lcombine lxs1 lxs2 (-)));;

ltake(5, (lcombine lxs1 lxs2 ( * )));;

ltake(10, (lcombine (lfrom 0) (lfrom 10) (+)));;

lcombine (lfrom 0) (lfrom 10) (+);;


MODYFIKACJA

OCaml

type 'a lbt = Empty | Node of 'a * 'a lbt Lazy.t * 'a lbt Lazy.t;;


let rec map f = function
	Empty 								 -> Empty
	| Node(value, lazy left, lazy right) -> Node(f value, lazy (map f left), lazy (map f right));;
	
	
let breadthLBT lt =
  let rec loop acc result =
    match acc with
	| []                          			-> result
	| Empty::t                    			-> loop t result
	| Node(value, lazy left, lazy right)::t -> loop(t@[left; right])(value::result)
  
  in match lt with
     | Empty -> []
	 | _     -> List.rev (loop [lt] []);;
	
	
let infTree = 
	let rec infTreeBuilder n =
		let subTree = lazy (infTreeBuilder (n+1)) 
		in Node(n, subTree, subTree)
	in infTreeBuilder 1;;
	
	
let take n lt =
  let rec loop n acc result =
    match (n, acc) with
	 | (0, _) | (_, [])  						  -> result
	 | (_, Empty::t)                    		  -> loop n t result
	 | (_, Node(value, lazy left, lazy right)::t) -> loop (n-1) (t @ [left; right]) (value::result)
  in match lt with
     | Empty -> []
	 | _     -> List.rev (loop n [lt] []);;
	

let tt = Node(1, lazy (Node(2, lazy (Node(4, lazy Empty, lazy Empty)), lazy Empty)), lazy (Node(3, lazy (Node(5, lazy Empty, lazy (Node(6, lazy Empty, lazy Empty)))), lazy Empty)));;


let increment x = x+1;;


let triple x = 3*x;;


let lt = map increment tt;;


let llt = map triple tt;; 


let linf1 = map increment infTree;;


let linf2 = map triple infTree;;


breadthLBT tt;;


breadthLBT lt;;


breadthLBT tt;;


breadthLBT llt;;


take 10 infTree;;


take 10 linf1;;


take 10 linf2;;

	
	
	








































let rec lsplit = function 
  | LNil 			     -> (LNil, LNil)
  | LCons (elem1, rest1) ->	match rest1() with
							| LNil 				   -> (LCons(elem1, function() -> LNil), LNil)
							| LCons (elem2, rest2) -> let f = function() -> lsplit(rest2()) in (LCons(elem1, function () -> fst(f())), LCons(elem2, function () -> snd(f())));;

