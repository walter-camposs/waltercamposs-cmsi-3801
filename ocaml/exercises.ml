exception Negative_Amount

let change amount =
  if amount < 0 then
    raise Negative_Amount
  else
    let denominations = [25; 10; 5; 1] in
    let rec aux remaining denominations =
      match denominations with
      | [] -> []
      | d :: ds -> (remaining / d) :: aux (remaining mod d) ds
    in
    aux amount denominations

let first_then_apply array predicate consumer =
  match List.find_opt predicate array with
  | Some x -> consumer x
  | None -> None

let powers_generator base = 
  let rec generate_power power () = 
    Seq.Cons (power, generate_power (power * base))
  in
  generate_power 1
  
let meaningful_line_count filename =
  let meaningful_line line = 
    let trimmed = String.trim line in
    String.length trimmed > 0 && not (String.starts_with ~prefix:"#" trimmed)
  in

  let the_file = open_in filename in
  let finally () = close_in the_file in

  let rec count_lines count =
    try
      let line = input_line the_file in 
      if meaningful_line line then
        count_lines (count + 1)
      else
        count_lines count
    with
    | End_of_file -> count
  in
  Fun.protect ~finally (fun () -> count_lines 0)

type shape = 
  | Sphere of float
  | Box of float * float * float

let surface_area s =
  match s with 
  | Sphere radius -> 4.0 *. Float.pi *. (radius**2.0)
  | Box (length, width, height) -> 2.0 *. length *. width +. 2.0 *. length *. height +. 2.0 *. height *. width

let volume s = 
  match s with 
  | Sphere radius -> (4.0 /. 3.0) *. Float.pi *. (radius**3.0)
  | Box (length, width, height) -> length *. width *. height

(* Write your binary search tree implementation here *)
type 'a binary_search_tree = 
	| Empty
	| Node of 'a binary_search_tree * 'a * 'a binary_search_tree

let rec size tree =
  match tree with
  | Empty -> 0
  | Node (left, _, right) -> 1 + size left + size right

let rec contains data tree =
  match tree with
  | Empty -> false
  | Node (left, value, right) -> 
    if data = value then
      true
    else if data < value then
      contains data left 
    else 
      contains data right

let rec insert data tree =
  match tree with 
  | Empty -> Node(Empty, data, Empty)
  | Node (left, value, right) -> 
    if data < value then 
      Node (insert data left, value, right)
    else if data > value then 
      Node (left, value, insert data right)
    else
      Node (left, value, right)

let rec inorder tree =
  match tree with
  | Empty -> []
  | Node (left, value, right) -> inorder left @ [value] @ inorder right