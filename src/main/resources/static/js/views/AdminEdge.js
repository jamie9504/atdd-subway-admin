import {optionTemplate, subwayLinesItemTemplate} from "../../utils/templates.js";
import {defaultSubwayLines} from "../../utils/subwayMockData.js";
import tns from "../../lib/slider/tiny-slider.js";
import {EVENT_TYPE} from "../../utils/constants.js";
import Modal from "../../ui/Modal.js";
import api from "../../api/index.js";

function AdminEdge() {
    const $subwayLinesSlider = document.querySelector(".subway-lines-slider");
    const createSubwayEdgeModal = new Modal();

    const initSubwayLinesSlider = () => {
        api.line.get("/stations").then(res => {
            $subwayLinesSlider.innerHTML = res
                .map(line => subwayLinesItemTemplate(line))
                .join("");
            tns({
                container: ".subway-lines-slider",
                loop: true,
                slideBy: "page",
                speed: 400,
                autoplayButtonOutput: false,
                mouseDrag: true,
                lazyload: true,
                controlsContainer: "#slider-controls",
                items: 1,
                edgePadding: 25
            });
        });
    };

    const initSubwayLineOptions = () => {
        const subwayLineOptionTemplate = defaultSubwayLines
            .map(line => optionTemplate(line.name))
            .join("");
        const $stationSelectOptions = document.querySelector(
            "#station-select-options"
        );
        $stationSelectOptions.insertAdjacentHTML(
            "afterbegin",
            subwayLineOptionTemplate
        );
    };

    const onRemoveStationHandler = event => {
        const $target = event.target;
        const isDeleteButton = $target.classList.contains("mdi-delete");
        if (!isDeleteButton) {
            return;
        }
        const lineId = $target.closest(".slider-list").querySelector(".lint-title").dataset.lineId;
        const stationId = $target.closest(".list-item").dataset.stationId;

        api.line.delete(`/${lineId}/stations/${stationId}`)
            .then(() =>
                $target.closest(".list-item").remove()
            ).catch(err => alert(err));
    };

    const initEventListeners = () => {
        $subwayLinesSlider.addEventListener(
            EVENT_TYPE.CLICK,
            onRemoveStationHandler
        );
    };

    this.init = () => {
        initSubwayLinesSlider();
        initSubwayLineOptions();
        initEventListeners();
    };
}

const adminEdge = new AdminEdge();
adminEdge.init();
