(defmacro safe [variable form]
	;`(let [locVar# variable] 
	`(let ~variable 
		(do ~form;)
		(if (instance? java.io.Closeable (get ~variable 0))
			(.close (get ~variable 0))
		))
	)
)

; Exemplet från uppgiftsbeskrivningen.
;(import java.io.FileReader java.io.File)
;(safe [s (new FileReader (new File "file.txt"))] (.read s))
