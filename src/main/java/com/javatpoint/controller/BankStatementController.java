package com.javatpoint.controller;
import java.io.*;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javatpoint.model.BankStatement;
import com.javatpoint.service.BankStatementService;


//creating RestController
@RestController
public class BankStatementController
{
//autowired the StudentService class
@Autowired
BankStatementService bankStatementService;



@GetMapping(value = "/account", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
ResponseEntity<ByteArrayResource> all(Date date) throws IOException {

    List<BankStatement> accounts = bankStatementService.getAllAccounts();

    byte[] byteArray = writeToCSV(accounts);

    ByteArrayResource resource = new ByteArrayResource(byteArray);
    String filename = "file.csv";
    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .contentLength(resource.contentLength())
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(resource);
}

    @GetMapping(value = "/value")
    double getValue(Date date) {

        List<BankStatement> accounts = bankStatementService.getAllAccounts();
        double value = 0.0;
        for (BankStatement item:
             accounts) {
            value += item.getAmount();
        }

        return value;
    }


    private static byte[] writeToCSV(List<BankStatement> bankStatementList)
    {
        try
        {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(baos));
            for (BankStatement bankStatement : bankStatementList)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(bankStatement.getAccountId());
                oneLine.append("\t");
                oneLine.append(bankStatement.getOpDate());
                oneLine.append("\t");
                oneLine.append(bankStatement.getBenef());
                oneLine.append("\t");
                oneLine.append(bankStatement.getComment());
                oneLine.append("\t");
                oneLine.append(bankStatement.getAmount());
                oneLine.append("\t");
                oneLine.append(bankStatement.getCurrency());
                oneLine.append("\t");
                bw.write(oneLine.toString());
                bw.newLine();
            }

            bw.flush();
            bw.close();

            byte[] bytes = baos.toByteArray();
            return bytes;


        }
        catch (UnsupportedEncodingException e) {
            int a= 0;
        }
        catch (FileNotFoundException e){
            int a= 0;
        }
        catch (IOException e){
            int a= 0;
        }

        return null;
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST, consumes="application/json")
    BankStatement newEmployee(@RequestBody BankStatementHelper bytes) {


        String s = new String(bytes.bytes);
        s = s.replaceAll("[\\r\\n]+",";");
        String[] a = s.split(";");


        int statementCount = a.length / 6;

        for (int  i = 0; statementCount > i; statementCount--)
        {
            BankStatement newStatement = new BankStatement();
            newStatement.setAccountId(a[0]);
            newStatement.setBenef(a[1]);
            newStatement.setAmount(Double.parseDouble(a[2]));
            newStatement.setComment(a[3]);
            newStatement.setCurrency(a[4]);
            newStatement.setOpDate( new Date());

            bankStatementService.saveOrUpdate(newStatement);
        }


        return null;
    }



}



