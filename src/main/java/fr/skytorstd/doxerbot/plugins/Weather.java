package fr.skytorstd.doxerbot.plugins;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import fr.skytorstd.doxerbot.App;
import fr.skytorstd.doxerbot.databases.ConfigurationPluginsDatabase;
import fr.skytorstd.doxerbot.embedCrafter.ErrorCrafter;
import fr.skytorstd.doxerbot.embedCrafter.WeatherCrafter;
import fr.skytorstd.doxerbot.manager.Logger;
import fr.skytorstd.doxerbot.messages.LoggerMessages;
import fr.skytorstd.doxerbot.messages.WeatherMessages;
import fr.skytorstd.doxerbot.object.Plugin;
import fr.skytorstd.doxerbot.states.PluginName;
import fr.skytorstd.doxerbot.states.QueueAfterTimes;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Weather extends ListenerAdapter {

    public Weather() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("/weather");
        commands.add("/weather [ville]");
        Plugin weatherPlugin = new Plugin(PluginName.WEATHER.getMessage(), WeatherMessages.DESCRIPTION.getMessage(), commands);

        App.addPlugin(weatherPlugin);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        if(e.getName().equals("weather")){
            if(ConfigurationPluginsDatabase.getStatePluginWithPluginName(PluginName.WEATHER.getMessage(), e.getGuild().getId())){
                String ville = "";
                if(e.getOptions().size() == 0)
                    ville = "Sète";
                else
                    ville = e.getOption("ville").getAsString();

                OpenWeatherMapClient openWeatherClient = new OpenWeatherMapClient("08405ba2c247bb8543d82316fe5fb107");
                openWeatherClient.setReadTimeout(1000);
                openWeatherClient.setConnectionTimeout(1000);

                final com.github.prominence.openweathermap.api.model.weather.Weather weather = openWeatherClient.currentWeather().single().byCityName(ville).language(Language.FRENCH).unitSystem(UnitSystem.METRIC).retrieve().asJava();
                e.replyEmbeds(WeatherCrafter.craftEmbedWeather(weather)).queue();
            }else {
                e.replyEmbeds(ErrorCrafter.errorConfigurationCrafter(PluginName.WEATHER)).setEphemeral(true).queue((m) -> m.deleteOriginal().queueAfter(QueueAfterTimes.ERROR_TIME.getQueueAfterTime(), TimeUnit.SECONDS));
                Logger.getInstance().toLog(PluginName.WEATHER.getMessage(), LoggerMessages.PLUGIN_CONFIGURATION_DESACTIVE.getMessage(), e.getGuild(), e.getMember(), false);
            }
        }
    }

}
