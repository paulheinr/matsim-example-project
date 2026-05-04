import matsim


def print_activity_times(population_file):
    plans = matsim.plan_reader(
        population_file,
        selected_plans_only=True
    )

    for person, plan in plans:
        person_id = person.attrib["id"]
        if plan is None:
            continue
        for element in plan:
            if element.tag == "activity":
                act_type = element.attrib.get("type")
                start = element.attrib.get("start_time")
                end = element.attrib.get("end_time")
                max_dur = element.attrib.get("max_dur")
                print(
                    f"person={person_id} | "
                    f"activity={act_type} | "
                    f"start={start} | "
                    f"end={end} | "
                    f"max_dur={max_dur} | "
                )


if __name__ == "__main__":
    population_file = "../../../output/output_plans.xml.zst"
    print_activity_times(population_file)