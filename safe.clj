(defmacro safe [variable form]
	;`(let [locVar# variable] 
	`(let ~variable
		(if (instance? java.io.Closeable (get ~variable 0))
			~form
			; Exception!!!!
		)
	)
)

; Exemplet från uppgiftsbeskrivningen.
;(import java.io.FileReader java.io.File)
;(safe [s (new FileReader (new File "file.txt"))] (.read s))
