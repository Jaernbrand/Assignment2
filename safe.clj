(defmacro safe [variable form]
	`(variable )
)

; Exemplet från uppgiftsbeskrivningen.
(import java.io.FileReader java.io.File)
(safe [s (new FileReader (new File "file.txt"))] (.read s))
