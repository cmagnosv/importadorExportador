package view;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Arquivo {
String Origem;
String Destino;
String resultado ;

public String converte(Scanner scanner, String saida, String data, String codevento ){

	try {
        
        FileWriter outputFileWriter = new FileWriter(saida); // configura��o de arquivo de saida 

        Double valorDesc = 0.00; // variavel para guardar e calcular o valor a descontar
       
        String[] line = pegaLinha(scanner); // metodo para pega a os dsados atuais e avan�ar na proxima linha

        String dado1 = line[7]; // variavel para armazenar a matricula anterior
        
        while (scanner.hasNextLine())  { // inicio de loop enquanto possui linha

        	String[] exportar = pegaLinha(scanner);
            String dado1c = exportar[7]; // na posi��o 7 encontra-se  matricula do colaborador

        
            if(dado1.equalsIgnoreCase(dado1c)){ // compara se a matricula anterior e igual a atual, se for ele soma ate ficar diferente

            	valorDesc += formataDouble(exportar[28]); // somando enquanto as matriculas s�o iguais
            }else{ // quando a matricula for diferente ele encerra o calculo

            	if(valorDesc >0){ // verifica se n�o estamos na primeira entrada que a chapa esta vazia ou n�o t�m valor total gerado, se for ele ignora esta ,linha 
            		String sa = formataChapa(dado1)+"        "+data+codevento+"000:00           0.00    "+formataValor(valorDesc)+"           0.00N\n"; // dado no padr�o aceito pela sistema que vai importar o aerquivo
	                outputFileWriter.write(sa);  // grava a linha no arquivo
            	
            	}
                dado1 = dado1c; // atualiza o dado da matricula atual
                valorDesc=formataDouble(exportar[28]); // fomatar o texto para float
                
            }
        }
        
        //grava no arquivo os dados do ultimo agrupamento do loop
        String sa = formataChapa(dado1)+"        "+data+codevento+"000:00           0.00    "+formataValor(valorDesc)+"           0.00N\n"; // dado no padr�o aceito pela sistema que vai importar o aerquivo
        outputFileWriter.write(sa);  // grava a linha no arquivo

        scanner.close();         // terminou o loop fecha o scanner
        outputFileWriter.close(); // e fecha o arquivo
        
        resultado = " Arquivo gerado !"; // envia em caso de sucesso da execu��o 

		
        } catch (IOException e1) {
           // System.out.println("Ocorreu um erro ao processar o arquivo: " + e1.getMessage());
            resultado =" MAS Ocorreu um erro ao processar o arquivo: " + e1.getMessage(); // envia em caso de alguma falha
        }
	
	
	return resultado; // resultado da execu��o
	
}

private String[] pegaLinha(Scanner scanner){
	String line = scanner.nextLine(); //captura a linha para tratamento
	return line.split(";");
}


private String formataChapa(String valor) { // metodo formatar o campo matricula de acordo com o pedido
	String vl =  String.valueOf(Integer.parseInt(valor)) ; // converte em n�mero para retirar esp�os em branco e zeros a esquerda
	
	int qtd = 8 - vl.length(); // verificar a quantidade de valores necess�rios para a formata��o requerida, s�o 8 caractrese - o valor extraido
    StringBuilder pattern = new StringBuilder(); 
    for(int i=0; i<qtd; i++) { // loop para montar os zeros necessarios para a formata��o da matricula
        pattern.append("0");
    }

    return  pattern+vl; // devole a matricula no tamanho e formato requerido

}



private Double formataDouble(String valor) { // converte o valor em float

	return Double.parseDouble(valor.replace(",", "."));
}

private String formataValor(Double valor) { // formata para string colocando o valor na formata��o exigida pela importa��o, 11  caracteres - o valor, sendo o retsante espa�o em branco a esquerda

	DecimalFormat f = new DecimalFormat("#.00"); // for�a o n�mero para ficar com duas cass decimais depois do ponto
	
	String v= f.format(valor).replace(",", ".");
	int qtd1 = 11-v.length(); //  verificar a quantidade de valores necess�rios para a formata��o requerida, s�o 11 caractrese - o valor extraido
    StringBuilder pattern = new StringBuilder();
    for(int i=0; i<qtd1; i++) { // loop para montar os zeros necessarios para a formata��o do valor 
        pattern.append(" ");
    }

	return pattern+v;
}

}
