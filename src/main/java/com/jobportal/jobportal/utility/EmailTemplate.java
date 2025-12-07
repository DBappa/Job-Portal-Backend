package com.jobportal.jobportal.utility;

public class EmailTemplate {

    public static String generateOtpEmailHtml(String userName, String otp, int expiryMinutes) {

        String html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Your OTP Code</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f5f7fa;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: auto;
                        background: #ffffff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                    }
                    .header {
                        background: #4f46e5;
                        padding: 16px;
                        text-align: center;
                        color: white;
                        font-size: 20px;
                        font-weight: bold;
                        border-radius: 8px 8px 0 0;
                    }
                    .otp-box {
                        font-size: 32px;
                        letter-spacing: 4px;
                        text-align: center;
                        font-weight: bold;
                        color: #111827;
                        background: #f3f4f6;
                        padding: 12px;
                        border-radius: 6px;
                        margin: 20px 0;
                    }
                    .footer {
                        font-size: 12px;
                        text-align: center;
                        color: #6b7280;
                        margin-top: 20px;
                    }
                    .info {
                        font-size: 14px;
                        color: #374151;
                        margin-top: 10px;
                    }
                </style>
            </head>
            <body>
                <div class="container">

                    <div class="header">Your One-Time Password (OTP)</div>

                    <p>Hi <strong>%s</strong>,</p>

                    <p class="info">
                        Use the following One-Time Password (OTP) to complete your verification process.
                    </p>

                    <div class="otp-box">%s</div>

                    <p class="info">
                        This OTP is valid for <strong>%d minutes</strong>. 
                        Do not share it with anyone.
                    </p>

                    <p class="info">
                        If you did not request this verification, please ignore this email
                        or contact support immediately.
                    </p>

                    <div class="footer">
                        © %d Clifav Technologies Pvt. Ltd.  
                        <br>All rights reserved.
                    </div>
                </div>
            </body>
            </html>
            """;

        return html.formatted(userName, otp, expiryMinutes, java.time.Year.now().getValue());
    }


    public static String getSubject() {
        return "Your OTP Code for Verification";
    }
}

