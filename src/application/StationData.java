package application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationData {
    
    private final Map<String, List<Station>> stationMap;

    public StationData() {
        stationMap = new HashMap<>();
        initializeStations();
    }

    private void initializeStations() {
        // Irish
        stationMap.put("Ireland", Arrays.asList(
            new Station("RTE Radio 1", "https://liveaudio.rte.ie/hls-radio/ieradio1/chunklist.m3u8", "/assets/IRE/rte1.png"),
            new Station("RTE Radio 2", "https://liveaudio.rte.ie/hls-radio/ie2fm/chunklist.m3u8", "/assets/IRE/2fm.png"),
            new Station("RTE Lyric FM", "https://liveaudio.rte.ie/hls-radio/lyric/chunklist.m3u8", "/assets/IRE/lyric.png"),
            new Station("Newstalk", "https://www.goloudplayer.com/radio/newstalk", "/assets/IRE/newstalk.png"),  // NOT WORKING
            new Station("Radio 1 Extra", "https://22733.live.streamtheworld.com/RTE_RADIO_1_EXTRAAAC/HLS/e3f1be02-3bf3-4201-970e-6b3739c66bff/0/playlist.m3u8", "/assets/IRE/extra.png"),  // NOT WORKING
            new Station("Radio na Gaeltachta", "https://liveaudio.rte.ie/hls-radio/rnag/chunklist.m3u8", "/assets/IRE/rnag.png")
        ));

        // UK
        stationMap.put("England", Arrays.asList(
            new Station("BBC Radio 1", "https://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_one/bbc_radio_one.isml/bbc_radio_one-audio%3d96000.norewind.m3u8", "/assets/UK/bbc1.png"),
            new Station("BBC Radio 2", "https://as-hls-ww.live.cf.md.bbci.co.uk/pool_904/live/ww/bbc_radio_two/bbc_radio_two.isml/bbc_radio_two-audio%3d96000.norewind.m3u8", "/assets/UK/bbc2.png"),
            new Station("BBC Radio 3", "https://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_three/bbc_radio_three.isml/bbc_radio_three-audio%3d96000.norewind.m3u8", "/assets/UK/bbc3.png"),
            new Station("BBC Radio 4", "https://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_fourfm/bbc_radio_fourfm.isml/bbc_radio_fourfm-audio%3d96000.norewind.m3u8", "/assets/UK/bbc4.png"),
            new Station("Classic FM", "https://classical.icecast.solhost.co.uk/classicfm", "/assets/UK/bbc1.png"),  // NOT WORKING
            new Station("Radio 1 Xtra", "http://stream.live.vc.bbcmedia.co.uk/bbc_radio_one_xtra", "/assets/UK/bbc1x.png")  // NOT WORKING
        ));

        // USA
        stationMap.put("USA", Arrays.asList(
            new Station("NPR", "https://npr-ice.streamguys1.com/live", "/assets/USA/kexp.png"),
            new Station("KEXP", "http://live-streaming.kexp.org/kexp/128k", "/assets/USA/kexp.png"),
            new Station("WSB Radio", "http://wsbradio.com/live", "/assets/USA/kexp.png"),
            new Station("WNYC", "https://www.wnyc.org/stream", "/assets/USA/kexp.png"),
            new Station("KQED", "https://www.kqed.org/radio/live", "/assets/USA/kexp.png"),
            new Station("Radio Paradise", "https://stream.radioparadise.com/mp3-192", "/assets/USA/kexp.png")
        ));

        // france
        stationMap.put("France", Arrays.asList(
            new Station("France Inter", "http://direct.franceinter.fr/live/franceinter-midfi.mp3", "/assets/FRA/franceInter.png"),
            new Station("NRJ", "http://stream.nrj.fr/nrj", "/assets/FRA/franceInter.png"),
            new Station("Europe 1", "http://europe1-vod.cdn.dvmr.fr/europe1.mp3", "/assets/FRA/franceInter.png"),
            new Station("Radio France", "http://www.radiofrance.fr/live", "/assets/FRA/franceInter.png"),
            new Station("Fun Radio", "http://www.funradio.fr/stream", "/assets/FRA/franceInter.png"),
            new Station("RMC", "http://rmc.bfmtv.com/live", "/assets/FRA/franceInter.png")
        ));

        // German
        stationMap.put("Germany", Arrays.asList(
            new Station("Deutschlandfunk", "https://stream.dlf.de/dlf/streams/dlf.m3u"		, "/assets/DEU/dwd.png"),
            new Station("Radio Eins", "https://www.radioeins.de/live/radioeins.m3u"			, "/assets/DEU/dwd.png"),
            new Station("HR3", "https://www.hr3.de/streams/hr3.m3u"							, "/assets/DEU/dwd.png"),
            new Station("WDR 2", "https://wdr2.de/live/wdr2.m3u"							, "/assets/DEU/dwd.png"),
            new Station("SWR3", "https://www.swr3.de/streams/swr3.m3u"						, "/assets/DEU/dwd.png"),
            new Station("Antenne Bayern", "https://www.antenne.de/live/antenne-bayern.m3u"	, "/assets/DEU/dwd.png")
        ));

        // Japan
        stationMap.put("Japan", Arrays.asList(
            new Station("NHK World Radio", "https://www3.nhk.or.jp/nhkworld/en/radio/", "/assets/JPN/NHK.png"),
            new Station("Tokyo FM", "https://www.tokyofm.co.jp/", "/assets/JPN/NHK.png"),
            new Station("FM Yokohama", "https://www.fmyokohama.co.jp/", "/assets/JPN/NHK.png"),
            new Station("J-Wave", "http://www.j-wave.co.jp/", "/assets/JPN/NHK.png"),
            new Station("InterFM897", "https://www.interfm.co.jp/", "/assets/JPN/NHK.png"),
            new Station("FM802", "http://fm802.com/", "/assets/JPN/NHK.png")
        ));
    }


    public List<Station> getStationsByCountry(String country) {
        return stationMap.getOrDefault(country, Arrays.asList());
    }
}
