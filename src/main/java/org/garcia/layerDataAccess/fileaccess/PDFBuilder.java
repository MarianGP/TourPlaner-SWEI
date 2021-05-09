package org.garcia.layerDataAccess.fileaccess;

import com.itextpdf.html2pdf.HtmlConverter;
import org.garcia.layerBusiness.util.SecondsToTime;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.TourStats;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PDFBuilder {

    public static void createSummaryPdf(String url, List<Tour> tourList, TourStats stats) throws IOException {
        StringBuilder sbHtml = new StringBuilder();

        sbHtml.append(
                "<html lang=\"en\" dir=\"ltr\">\n" +
                        "  <head>\n" +
                        "    <meta charset=\"utf-8\">\n" +
                        "    <title>Report</title>\n" +
                        "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css\" integrity=\"sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l\" crossorigin=\"anonymous\">\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <div class=\"container-fluid \">\n" +
                        "      <h2>Tour Planer - Summary Report\n" +
                        "        <img src=\"C:\\Users\\mgarc\\IdeaProjects\\Projects\\TourPlaner_SWE\\src\\main\\resources\\org\\garcia\\img\\logo.jpg\"\n" +
                        "        alt=\"logo\" class=\"img-fluid m-auto\" style=\"width:100px\">\n" +
                        "      </h2><br><br>\n" +
                        "      <div class=\"container\">\n" +
                        "        <table class=\"table table-bordered\">\n" +
                        "          <thead class=\"thead\">\n" +
                        "            <tr>\n" +
                        "              <th>Total Duration</th>\n" +
                        "              <th>Total Distance</th>\n" +
                        "              <th>Walked km/time</th>\n" +
                        "              <th>Biked km/time</th>\n" +
                        "              <th>Hiked km/time</th>\n" +
                        "              <th>Run km/time</th>\n" +
                        "            </tr>\n" +
                        "          </thead>\n" +
                        "\n" +
                        "          <tbody>\n" +
                        "          <tr>\n" +
                        "            <td>" + SecondsToTime.convertSecToTime(stats.getTotalTime()) + "</td>\n" +
                        "            <td>" + stats.getTotalDistance() + "</td>\n" +
                        "            <td>" + stats.getWalkedKm() + " / " + SecondsToTime.convertSecToTime(stats.getWalkedTime()) + "</td>\n" +
                        "            <td>" + stats.getBikedKm() + " / " + SecondsToTime.convertSecToTime(stats.getBikedTime()) + "</td>\n" +
                        "            <td>" + stats.getHikedKm() + " / " + SecondsToTime.convertSecToTime(stats.getHikedTime()) + "</td>\n" +
                        "            <td>" + stats.getRunKm() + " / " + SecondsToTime.convertSecToTime(stats.getRunTime())+ "</td>\n" +
                        "          </tr>\n" +
                        "          </tbody>\n" +
                        "\n" +
                        "        </table><br>\n" +
                        "      </div><br>\n" +
                        "\n" +
                        "      <h3>All Tours</h3><br><br>\n" +
                        "      <div class=\"container\">\n" +
                        "        <table class=\"table table-bordered\">\n" +
                        "          <thead class=\"thead\">\n" +
                        "            <tr>\n" +
                        "              <th>N°</th>\n" +
                        "              <th>Title</th>\n" +
                        "              <th>Origin</th>\n" +
                        "              <th>Destination</th>\n" +
                        "              <th>Distance</th>\n" +
                        "              <th>Duration</th>\n" +
                        "            </tr>\n" +
                        "          </thead>\n" +
                        "\n" +
                        "          <tbody>\n"
        );

        int i = 0;
        for (Tour tour : tourList) {
            i++;
            sbHtml.append(  "          <tr>\n" +
                            "            <td>" + i + "</td>\n" +
                            "            <td>" + tour.getTitle() + "</td>\n" +
                            "            <td>" + tour.getOrigin() + "</td>\n" +
                            "            <td>" + tour.getDestination() + "</td>\n" +
                            "            <td>" + tour.getDistance() + "</td>\n" +
                            "            <td>" + SecondsToTime.convertSecToTime(tour.getDuration()) + "</td>\n" +
                            "          </tr>\n");
        }


        sbHtml.append(
                "          </tbody>\n" +
                        "\n" +
                        "        </table><br>\n" +
                        "      </div>\n" +
                        "\n" +
                        "    </div>\n" +
                        "  </body>\n" +
                        "</html>"
        );

        createReportFromHtml(url, String.valueOf(sbHtml));
    }

    public static void createTourPdf(Tour currentTour, String url, List<TourLog> tourLogs) throws IOException {
        Path configDirectory = Paths.get("src", "main", "resources");
        String imageUrl = configDirectory.toFile().getAbsolutePath() + "\\" + currentTour.getImg();

        StringBuilder sbHtml = new StringBuilder();

        sbHtml.append(
                "<html lang=\"en\" dir=\"ltr\">" +
                        "  <head>" +
                        "    <meta charset=\"utf-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                        "    <title>Report</title>" +
                        "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css\" integrity=\"sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l\" crossorigin=\"anonymous\">\n" +
                        "  </head>" +
                        "  <body class=\"small\"><small>" +
                        "    <div class=\"container-fluid \">" +
                        "      <br><h2>Tour Report " +
                        "        <img src=\"C:\\Users\\mgarc\\IdeaProjects\\Projects\\TourPlaner_SWE\\src\\main\\resources\\org\\garcia\\img\\logo.jpg\"\n" +
                        "        alt=\"logo\" class=\"img-fluid m-auto\" style=\"width:100px\">\n" +
                        "      </h2><br>" +
                        "      <div class=\"container\">" +
                        "        <table class=\"table table-bordered\">" +
                        "          <thead class=\"thead\">" +
                        "            <tr>" +
                        "              <th>Title</th>" +
                        "              <th>Origin</th>" +
                        "              <th>Destination</th>" +
                        "              <th>Distance</th>" +
                        "              <th>Duration</th>" +
                        "            </tr>" +
                        "          </thead>" +
                        "          <tbody>" +
                        "          <tr>" +
                        "            <td>" + currentTour.getTitle() + "</td>" +
                        "            <td>" + currentTour.getOrigin() + "</td>" +
                        "            <td>" + currentTour.getDestination() + "</td>" +
                        "            <td>" + currentTour.getDistance() + "</td>" +
                        "            <td>" + SecondsToTime.convertSecToTime(currentTour.getDuration()) + "</td>" +
                        "          </tr>" +
                        "          </tbody>" +
                        "        </table>" +
                        "        <br>" +
                        "        <h4> Description</h4><p>" + currentTour.getDescription() + "</p></h4>" +
                        "        <br>" +
                        "        <img src=\"" + imageUrl + "\" alt=\"\" class=\"img-fluid\" style=\"width:400px;\">" +
                        "      </div>" +
                        "      <h3>Logs</h3><br>" +
                        "      <table class=\"table table-striped table-bordered\">" +
                        "        <thead class=\"thead\">" +
                        "          <tr>" +
                        "            <th scope=\"col\">N°</th>" +
                        "            <th>Distance</th>" +
                        "            <th>Duration</th>" +
                        "            <th>Date</th>" +
                        "            <th>Rating</th>" +
                        "            <th>Sport</th>" +
                        "            <th>Avg speed</th>" +
                        "            <th>Start</th>" +
                        "            <th>End</th>" +
                        "            <th>Cadence Steps</th>" +
                        "          </tr>" +
                        "        </thead>" +
                        "        <tbody>"
        );

        if (tourLogs.isEmpty()) {
            sbHtml.append(
                    "<p>This tour doesn't have any logs yet.</p>"
            );
        } else {
            int i = 0;
            for (TourLog log : tourLogs) {
                i++;
                sbHtml.append(
                        "          <tr>" +
                                "            <th>" + i + "</th>" +
                                "            <th>" + log.getDistance() + "</th>" +
                                "            <th>" + log.getDuration() + "</th>" +
                                "            <th>" + log.getDate() + "</th>" +
                                "            <th>" + log.getRating() + "</th>" +
                                "            <th>" + log.getSport() + "</th>" +
                                "            <th>" + log.getAvg_speed() + "</th>" +
                                "            <th>" + log.getStart().getHour() + ":" + log.getStart().getMinute() + "</th>" +
                                "            <th>" + log.getEnd().getHour() + ":" + log.getEnd().getMinute() + "</th>" +
                                "            <th>" + log.getSpecial() + "</th>" +
                                "          </tr>"
                );
            }
        }

        sbHtml.append(
                "         </tbody>" +
                        "      </table>" +
                        "    </div>" +
                        "  </small></body></html>"
        );

        createReportFromHtml(url, String.valueOf(sbHtml));
    }

    public static void createReportFromHtml(String url, String html) throws IOException {
        String[] urlElements = url.split("\\\\");
        String baseUrl = url.replace(urlElements[urlElements.length - 1], "");
        File file = new File(baseUrl);
        file.mkdirs();
        HtmlConverter.convertToPdf(html, new FileOutputStream(url));
    }


}
