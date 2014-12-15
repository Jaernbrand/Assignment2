(def <> "notEqual")

;;from
(defmacro from [table]
	`(if (set? ~table)
		(do ~table)
		(throw (Exception. "Table is not a set!"))
	)
)

;;extractRowValues
(defn extractRowValues [row selKeys]
	(let [extRow {}] 
		(if ( > (count selKeys) 1)
			(conj
				(assoc extRow (first selKeys) (get row (first selKeys)))	
				(conj extRow (extractRowValues row (rest selKeys)))
			)
			
			(assoc extRow (first selKeys) (get row (first selKeys)))	
		)
	)	
)

;;extractColumns
(defn extractColumns [table selKeys]
	(if ( > (count table) 1) 
		(set 
			(concat
				(conj #{} (extractRowValues (first table) selKeys))
				(extractColumns (rest table) selKeys)
			)
		)

		(set (conj #{} (extractRowValues (first table) selKeys)))
	)
)

;;orderby
(defn orderby [table orderCol]
	(into (sorted-set-by 
		(fn [lhs rhs] 
			(compare (get lhs orderCol) (get rhs orderCol))
		)) 
	table)
)

;;where
(defmacro where [kw opList table] ;;keyword opList table
	`(let[op1#(first ~(vec opList))]
		(let[op2#(last ~(vec opList))]
			(if(= op1# "notEqual") 	
				(set (filter #(not= (~kw %) op2#) ~table))
				(set (filter #(op1# (~kw %) op2#) ~table))
			)
		)
	)
)	

;;select
(defmacro select ([columns from table]
		`(let [tbl# (~from ~table)]
			(extractColumns tbl# ~columns)
		)
	) 

	([columns from table ordr ordrCol]
		`(let [tbl# (~from ~table)]
			(do 
				(~ordr (extractColumns tbl# ~columns) ~ordrCol)
			)
		)
	)

	([columns from table where col opList]
		`(let [tbl# (~from ~table)]
			(do 
				(where ~col ~opList (extractColumns tbl# ~columns))
			)
		)
	)

	([columns from table where col opList ordr ordrCol]
		`(let [tbl# (~from ~table)]
			(do 
				(~ordr (where ~col ~opList (extractColumns tbl# ~columns)) ~ordrCol)
			)
		)
	)
)
	
