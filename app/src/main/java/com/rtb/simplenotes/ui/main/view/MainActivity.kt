package com.rtb.simplenotes.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.rtb.andbeyondmedia.banners.BannerAdListener
import com.rtb.andbeyondmedia.common.AdRequest
import com.rtb.simplenotes.adapter.NotesAdapter
import com.rtb.simplenotes.baseclasses.BaseActivity
import com.rtb.simplenotes.databinding.ActivityMainBinding
import com.rtb.simplenotes.ui.main.model.Note
import com.rtb.simplenotes.ui.main.viewmodel.MainViewModel
import com.rtb.simplenotes.utils.Params
import org.koin.android.ext.android.inject
import org.prebid.mobile.BannerAdUnit
import org.prebid.mobile.Host
import org.prebid.mobile.OnCompleteListener
import org.prebid.mobile.OnCompleteListener2
import org.prebid.mobile.PrebidMobile
import org.prebid.mobile.ResultCode
import org.prebid.mobile.addendum.AdViewUtils
import org.prebid.mobile.addendum.PbFindSizeError
import org.prebid.mobile.api.data.InitializationStatus
import org.prebid.mobile.api.exceptions.InitError
import org.prebid.mobile.rendering.listeners.SdkInitializationListener

const val TAG = "Ads"

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        init()
        getNotes()
        startGAM()
        loadAd()
    }

    private fun init() {
        binding.notes.apply {
            val layoutManager: GridLayoutManager by inject()
            this.layoutManager = layoutManager
            this@MainActivity.adapter = NotesAdapter(this@MainActivity, listOf(), this@MainActivity::openNote, this@MainActivity::deleteNote)
            this.adapter = this@MainActivity.adapter
        }
        binding.add.setOnClickListener { startNewActivity(AddNoteActivity::class.java) }
    }

    private fun getNotes() {
        viewModel.getRealTimeNotes().observe(this) { notes ->
            binding.createFirstNote.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
            adapter.addNotes(notes)
        }
    }

    private fun openNote(note: Note) {
        startNewActivity(Intent(this, AddNoteActivity::class.java).putExtra(
                Params.NOTE, note
        ))
    }

    private fun deleteNote(note: Note) {
        viewModel.deleteNote(note.objectBoxID)
    }

    private fun loadAd() {
        val adRequest = AdRequest().Builder().addCustomTargeting("hb_format", "amp").build()
        binding.adView.loadAd(adRequest)

        binding.adView.setAdListener(object : BannerAdListener {
            override fun onAdClicked() {
            }

            override fun onAdClosed() {
            }

            override fun onAdFailedToLoad(error: String) {
                Log.d(TAG, "onAdFailedToLoad: $error")
            }

            override fun onAdImpression() {
            }

            override fun onAdLoaded() {
            }

            override fun onAdOpened() {
            }
        })

    }


    private fun startGAM() {
        MobileAds.initialize(this) {}
        enablePrebid()
        loadGAMAd()
    }

    private fun enablePrebid() {
        PrebidMobile.setPrebidServerHost(Host.createCustomHost("https://ib.adnxs.com/openrtb2/prebid"))
        PrebidMobile.setPrebidServerAccountId("13903")
        PrebidMobile.setTimeoutMillis(1000)
        PrebidMobile.initializeSdk(this, object : SdkInitializationListener {
            override fun onInitializationComplete(status: InitializationStatus) {
                Log.i(TAG, "Prebid Initialized")
            }

            override fun onSdkInit() {
                Log.i(TAG, "Prebid Initialized")
            }

            override fun onSdkFailedToInit(error: InitError?) {
                Log.e(TAG, error?.error ?: "")
            }
        })
    }

    private fun loadGAMAd() {
        val adRequest = AdManagerAdRequest.Builder().addCustomTargeting("hb_format", "amp").build()
        binding.adView2.adListener = object : AdListener() {

            override fun onAdLoaded() {
                super.onAdLoaded()
               /* AdViewUtils.findPrebidCreativeSize(binding.adView2, object : AdViewUtils.PbFindSizeListener {
                    override fun success(width: Int, height: Int) {
                        binding.adView2.setAdSizes(AdSize(width, height))
                    }

                    override fun failure(error: PbFindSizeError) {}
                })*/
                //binding.adView2.recordManualImpression()
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d(TAG, "onAdImpression: ")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d(TAG, "onAdFailedToLoad: ${p0.message}")
            }
        }
      /*  fetchDemand(adRequest){
            Log.d(TAG, "loadGAMAd: Prebid fetched")
            binding.adView2.loadAd(adRequest)
        }*/
        binding.adView2.loadAd(adRequest)
    }

     /*private fun fetchDemand(adRequest: AdManagerAdRequest, callback: () -> Unit) {
         val totalSizes = arrayListOf(AdSize.BANNER, AdSize.LARGE_BANNER, AdSize.MEDIUM_RECTANGLE)
         val firstAdSize = totalSizes[0]
         val adUnit = BannerAdUnit("13144370", firstAdSize.width, firstAdSize.height)
         totalSizes.forEach { adSize -> adUnit.addAdditionalSize(adSize.width, adSize.height) }
         adUnit.fetchDemand(adRequest) { resultCode ->
             Log.d(TAG, "fetchDemand: ${resultCode.name}")
             callback()
         }
     }*/
}