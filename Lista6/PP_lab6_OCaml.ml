OCaml

let pascalTrapezoidF indexOfLine =
	let rec addNumbersInLine lst accum = 
		match lst with
		| e1 :: e2 :: e3 :: _ -> addNumbersInLine (List.tl lst) ((e1+e2+e3) :: accum)
		| _                   -> accum
	
	in let rec createLine indexOfLine lst =
		match indexOfLine with
		| 0 -> lst
		| _ -> createLine (indexOfLine-1) ([1;1] @ (addNumbersInLine lst [1;1]))
	
	in if indexOfLine < 0 then [] else createLine indexOfLine [1;1;1];;

pascalTrapezoidF (-1);;	
pascalTrapezoidF 0;;
pascalTrapezoidF 1;;
pascalTrapezoidF 2;;
pascalTrapezoidF 3;;
pascalTrapezoidF 4;;
pascalTrapezoidF 5;;
pascalTrapezoidF 6;;


#### TEN NAJBARDZIEJ PRZEJRZYSTY ####

OCaml

let pascalTrapezoidI indexOfLine =
  if indexOfLine < 0 then [||]
  
  else 
	let middle = ref 2 in
	let result = Array.make (2 * (indexOfLine + 1) + 1) 1 in 
	
	for _ = 0 to (indexOfLine-1) do
	  let previous1 = ref 1 in
	  let previous2 = ref 1 in
	  
	  for j = 2 to !middle do
	  
		let sum = !previous1 + !previous2 + result.(j) in
		previous1 := !previous2;
		previous2 := result.(j);
		result.(j) <- sum;
		result.(2 * !middle - j) <- sum
			 
	  done; middle := !middle + 1
    
	done; result;;
	
pascalTrapezoidI (-1);;	
pascalTrapezoidI 0;;
pascalTrapezoidI 1;;
pascalTrapezoidI 2;;
pascalTrapezoidI 3;;
pascalTrapezoidI 4;;
pascalTrapezoidI 5;;
pascalTrapezoidI 6;;


















OCaml

let pascalTrapezoidI indexOfLine =
  if indexOfLine <= 0 then [||]
  
  else 
	let middle = ref 2 in
	let result = Array.make (2 * (indexOfLine) + 1) 1 in 
	
	for _ = 0 to (indexOfLine-2) do
	  let previous1 = ref 1 in
	  let previous2 = ref 1 in
	  
	  for j = 2 to !middle do
	  
		let sum = !previous1 + !previous2 + result.(j) in
		previous1 := !previous2;
		previous2 := result.(j);
		result.(j) <- sum;
		result.(2 * !middle - j) <- sum
			 
	  done; middle := !middle + 1
    
	done; result;;
	
pascalTrapezoidI (-1);;	
pascalTrapezoidI 0;;
pascalTrapezoidI 1;;
pascalTrapezoidI 2;;
pascalTrapezoidI 3;;
pascalTrapezoidI 4;;
pascalTrapezoidI 5;;
pascalTrapezoidI 6;;








let buildTrapezoid indexOfLine =
	
	let rec printList lst =
	  match lst with
		| []   -> ()
		| h::t -> print_int h; print_string " "; printList t in
	
	let rec printTab howMuch tab =
	  match howMuch with
	    | 0 -> print_string tab
		| _ -> printTab (howMuch - 1) (" " ^ tab) in
	
	for i = 0 to indexOfLine do
		printTab (2 * (indexOfLine - i)) "";
		printList (Array.to_list (pascalTrapezoidI i));
		print_string "\n"
	done;;

buildTrapezoid 5;;
	
	



















#### STARA WERSJA Z FOR'AMI ####

OCaml

let pascalTrapezoidI indexOfLine =
  if indexOfLine < 0 then [||]
  
  else 
	let middle = indexOfLine + 1 in
	let result = Array.make (2*middle + 1) 1 in 
	let beginI = ref (middle - 1) in
	
	for _ = 0 to (indexOfLine-1) do
	  let previous = ref 1 in
	  
	  for j = (!beginI) to (middle - 1) do
	  
		if (j+1) = middle then result.(middle) <- (2 * !previous + result.(middle))
		
		else let sum = !previous + result.(j+1) + result.(j+2) in
			 previous := result.(j+1);
			 result.(j+1) <- sum;
			 result.(2*middle - (j+1)) <- sum
			 
	  done; beginI := !beginI - 1
    
	done; result;;
	
pascalTrapezoidI (-1);;	
pascalTrapezoidI 0;;
pascalTrapezoidI 1;;
pascalTrapezoidI 2;;
pascalTrapezoidI 3;;
pascalTrapezoidI 4;;
pascalTrapezoidI 5;;



#### STARA WERSJA Z WHILE'AMI ####

OCaml

let pascalTrapezoidI indexOfLine =
  if indexOfLine < 0 then [||]
  
  else 
	let middle = indexOfLine + 1 in
	let result = Array.make (2*middle + 1) 1 in 
	let beginI = ref (middle - 1) in
	
	while !beginI > 0 do
	  let previous = ref 1 in
	  let j = ref (!beginI) in
	  
	  while !j < middle do
	  
		if (!j + 1) = middle then result.(middle) <- (2 * !previous + result.(middle))
		
		else ( 
			 let sum = !previous + result.(!j+1) + result.(!j+2) in
			 previous := result.(!j+1);
			 result.(!j+1) <- sum;
			 result.(2*middle - (!j+1)) <- sum
			 );
		
		j := !j + 1
			 
	  done; beginI := !beginI - 1
    
	done; result;;
	
pascalTrapezoidI (-1);;	
pascalTrapezoidI 0;;
pascalTrapezoidI 1;;
pascalTrapezoidI 2;;
pascalTrapezoidI 3;;
pascalTrapezoidI 4;;
pascalTrapezoidI 5;;