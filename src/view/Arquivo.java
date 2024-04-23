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
        
        FileWriter outputFileWriter = new FileWriter(saida); // configuração de arquivo de saida 

        Double valorDesc = 0.00; // variavel para guardar e calcular o valor a descontar
       
        String[] line = pegaLinha(scanner); // metodo para pega a os dsados atuais e avançar na proxima linha

        String dado1 = line[7]; // variavel para armazenar a matricula anterior
        
        while (scanner.hasNextLine())  { // inicio de loop enquanto possui linha

        	String[] exportar = pegaLinha(scanner);
            String dado1c = exportar[7]; // na posição 7 encontra-se  matricula do colaborador

        
            if(dado1.equalsIgnoreCase(dado1c)){ // compara se a matricula anterior e igual a atual, se for ele soma ate ficar diferente

            	valorDesc += formataDouble(exportar[28]); // somando enquanto as matriculas são iguais
            }else{ // quando a matricula for diferente ele encerra o calculo

            	if(valorDesc >0){ // verifica se não estamos na primeira entrada que a chapa esta vazia ou não têm valor total gerado, se for ele ignora esta ,linha 
            		String sa = formataChapa(dado1)+"        "+data+codevento+"000:00           0.00    "+formataValor(valorDesc)+"           0.00N\n"; // dado no padrão aceito pela sistema que vai importar o aerquivo
	                outputFileWriter.write(sa);  // grava a linha no arquivo
            	
            	}
                dado1 = dado1c; // atualiza o dado da matricula atual
                valorDesc=formataDouble(exportar[28]); // fomatar o texto para float
                
            }
        }
        
        //grava no arquivo os dados do ultimo agrupamento do loop
        String sa = formataChapa(dado1)+"        "+data+codevento+"000:00           0.00    "+formataValor(valorDesc)+"           0.00N\n"; // dado no padrão aceito pela sistema que vai importar o aerquivo
        outputFileWriter.write(sa);  // grava a linha no arquivo

        scanner.close();         // terminou o loop fecha o scanner
        outputFileWriter.close(); // e fecha o arquivo
        
        resultado = " Arquivo gerado !"; // envia em caso de sucesso da execução 

		
        } catch (IOException e1) {
           // System.out.println("Ocorreu um erro ao processar o arquivo: " + e1.getMessage());
            resultado =" MAS Ocorreu um erro ao processar o arquivo: " + e1.getMessage(); // envia em caso de alguma falha
        }
	
	
	return resultado; // resultado da execução
	
}

private String[] pegaLinha(Scanner scanner){
	String line = scanner.nextLine(); //captura a linha para tratamento
	return line.split(";");
}


private String formataChapa(String valor) { // metodo formatar o campo matricula de acordo com o pedido
	String vl =  String.valueOf(Integer.parseInt(valor)) ; // converte em número para retirar espços em branco e zeros a esquerda
	
	int qtd = 8 - vl.length(); // verificar a quantidade de valores necessários para a formatação requerida, são 8 caractrese - o valor extraido
    StringBuilder pattern = new StringBuilder(); 
    for(int i=0; i<qtd; i++) { // loop para montar os zeros necessarios para a formatação da matricula
        pattern.append("0");
    }

    return  pattern+vl; // devole a matricula no tamanho e formato requerido

}



private Double formataDouble(String valor) { // converte o valor em float

	return Double.parseDouble(valor.replace(",", "."));
}

private String formataValor(Double valor) { // formata para string colocando o valor na formatação exigida pela importação, 11  caracteres - o valor, sendo o retsante espaço em branco a esquerda

	DecimalFormat f = new DecimalFormat("#.00"); // força o número para ficar com duas cass decimais depois do ponto
	
	String v= f.format(valor).replace(",", ".");
	int qtd1 = 11-v.length(); //  verificar a quantidade de valores necessários para a formatação requerida, são 11 caractrese - o valor extraido
    StringBuilder pattern = new StringBuilder();
    for(int i=0; i<qtd1; i++) { // loop para montar os zeros necessarios para a formatação do valor 
        pattern.append(" ");
    }

	return pattern+v;
}

}
